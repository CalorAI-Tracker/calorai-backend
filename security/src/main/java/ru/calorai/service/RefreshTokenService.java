package ru.calorai.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ru.calorai.jwToken.TokenHasher;
import ru.calorai.model.RefreshTokenEntity;
import ru.calorai.model.UserEntity;
import ru.calorai.repository.RefreshTokenRepository;
import ru.calorai.repository.UserRepository;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repo;
    private final UserRepository users;

    private final JwtService jwtService;

    private final TokenHasher tokenHasher;

    private final Clock clock = Clock.systemUTC();

    @Transactional
    public String issue(Long userId, String deviceId, String userAgent, String ip) {
        UserEntity user = users.findById(userId).orElseThrow();
        UUID jti = UUID.randomUUID();
        String token = jwtService.generateRefreshToken(user.getEmail(), user.getId(), jti);

        RefreshTokenEntity e = RefreshTokenEntity.builder()
                .jti(jti)
                .user(user)
                .tokenHash(tokenHasher.hash(token))
                .issuedAt(OffsetDateTime.now(clock))
                .expiresAt(OffsetDateTime.ofInstant(
                        jwtService.extractExpiresAtFromRefresh(token).toInstant(),
                        ZoneOffset.UTC)
                )
                .deviceId(deviceId)
                .userAgent(userAgent)
                .ipAddress(ip)
                .revoked(false)
                .build();
        repo.save(e);

        return token;
    }

    /**
     * Проверка + ротация: гасим старый, выдаём новый refresh и access.
     * Возвращаем pair(newAccess, newRefresh).
     */
    @Transactional
    public Pair<String,String> rotate(String presentedRefreshToken,
                                      Function<Long, Collection<? extends GrantedAuthority>> loadAuthorities,
                                      BiFunction<String, Long, String> accessTokenGenerator) {
        // криптографическая проверка refresh (подпись/issuer/type/срок)
        UUID jti = jwtService.extractJtiFromRefresh(presentedRefreshToken);
        DecodedJWT decoded = null;
        try {
            decoded = JWT.decode(presentedRefreshToken);
        } catch (JWTDecodeException ignore) {}

        // поиск записи в БД
        RefreshTokenEntity stored = repo.findByJti(jti)
                .orElseThrow(() -> new BadCredentialsException("refresh not found"));

        if (stored.getRevoked() || stored.isExpired(clock))
            throw new BadCredentialsException("refresh revoked/expired");

        // сверка ХЭША
        if (!tokenHasher.matches(presentedRefreshToken, stored.getTokenHash()))
            throw new BadCredentialsException("refresh mismatch");

        Long userId = stored.getUser().getId();
        String email = stored.getUser().getEmail();

        // ротация: пометить старый и выдать новый
        stored.setRevoked(true);
        UUID newJti = UUID.randomUUID();
        String newRefresh = jwtService.generateRefreshToken(email, userId, newJti);

        RefreshTokenEntity replacement = RefreshTokenEntity.builder()
                .jti(newJti)
                .user(stored.getUser())
                .tokenHash(tokenHasher.hash(newRefresh))
                .issuedAt(OffsetDateTime.now(clock))
                .expiresAt(OffsetDateTime.ofInstant(jwtService.extractExpiresAtFromRefresh(newRefresh).toInstant(), ZoneOffset.UTC))
                .build();
        repo.save(replacement);

        stored.setReplacedByJti(newJti);

        // 5) новый access
        Collection<? extends GrantedAuthority> authorities = loadAuthorities.apply(userId);
        String newAccess = accessTokenGenerator.apply(email, userId);

        return Pair.of(newAccess, newRefresh);
    }

    @Transactional
    public void revokeByJti(UUID jti) {
        repo.findByJti(jti).ifPresent(t -> t.setRevoked(true));
    }

    @Transactional
    public int revokeAllForUser(Long userId) {
        return repo.revokeAllByUserId(userId);
    }
}

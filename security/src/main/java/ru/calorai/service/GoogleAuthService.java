package ru.calorai.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.calorai.dto.response.TokenResponse;
import ru.calorai.jwToken.JwtProperties;
import ru.calorai.model.AuthProvider;
import ru.calorai.user.jpa.entity.UserEntity;
import ru.calorai.user.jpa.repository.RoleRepository;
import ru.calorai.user.jpa.repository.UserRepository;
import ru.calorai.heathProfile.enums.ERole;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoogleAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;

    public TokenResponse authenticate(String idTokenString, String deviceId,
                                      String userAgent, String ipAddress) {
        var payload = verifyGoogleToken(idTokenString);

        String email = payload.getEmail();
        String sub   = payload.getSubject();
        String name  = (String) payload.get("name");

        UserEntity user = userRepository.findByEmail(email)
                .map(u -> updateExistingUser(u, sub, name))
                .orElseGet(() -> createNewUser(email, sub, name));

        var authorities = roleRepository.findByUsersId(user.getId()).stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .toList();

        String accessToken  = jwtService.generateAccessToken(email, user.getId(), authorities);
        String refreshToken = refreshTokenService.issue(user.getId(), deviceId, userAgent, ipAddress);

        long expiresIn = jwtProperties.getAccessTokenExpirationMinutes() * 60L;
        return new TokenResponse(user.getId(), accessToken, refreshToken, "Bearer", expiresIn);
    }

    private GoogleIdToken.Payload verifyGoogleToken(String idTokenString) {
        try {
            var verifier = new GoogleIdTokenVerifier.Builder(
                    new NetHttpTransport(), new GsonFactory())
                    .setAudience(List.of(googleClientId))
                    .build();

            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new BadCredentialsException("Invalid Google token");
            }
            return idToken.getPayload();
        } catch (BadCredentialsException e) {
            throw e;
        } catch (Exception e) {
            throw new BadCredentialsException("Google token verification failed");
        }
    }

    private UserEntity updateExistingUser(UserEntity user, String sub, String name) {
        if (user.getProviderId() == null) {
            user.setProviderId(sub);
            user.setAuthProvider(AuthProvider.GOOGLE.name());
        }
        if (name != null && (user.getName() == null || user.getName().isBlank())) {
            user.setName(name);
        }
        user.setLastLoginAt(OffsetDateTime.now());
        return userRepository.save(user);
    }

    private UserEntity createNewUser(String email, String sub, String name) {
        var newUser = UserEntity.builder()
                .email(email)
                .name(name != null ? name : "Unknown")
                .providerId(sub)
                .authProvider(AuthProvider.GOOGLE.name())
                .emailVerified(true)
                .enabled(true)
                .createdAt(OffsetDateTime.now())
                .lastLoginAt(OffsetDateTime.now())
                .roles(new HashSet<>())
                .build();

        roleRepository.findByName(ERole.USER.name())
                .ifPresent(role -> newUser.getRoles().add(role));

        return userRepository.save(newUser);
    }
}

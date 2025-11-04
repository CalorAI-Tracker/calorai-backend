package ru.calorai.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import ru.calorai.jwToken.JwtProperties;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JWTVerifier accessVerifier;
    private final JWTVerifier refreshVerifier;

    private final Algorithm accessAlgorithm;
    private final Algorithm refreshAlgorithm;

    private final JwtProperties jwtProperties;

    public JwtService(@Qualifier("accessVerifier") JWTVerifier accessVerifier,
                      @Qualifier("refreshVerifier") JWTVerifier refreshVerifier,
                      @Qualifier("accessAlgorithm") Algorithm accessAlgorithm,
                      @Qualifier("refreshAlgorithm") Algorithm refreshAlgorithm,
                      JwtProperties jwtProperties) {
        this.accessVerifier = accessVerifier;
        this.refreshVerifier = refreshVerifier;
        this.accessAlgorithm = accessAlgorithm;
        this.refreshAlgorithm = refreshAlgorithm;
        this.jwtProperties = jwtProperties;
    }

    public String generateAccessToken(String email, Long userId, Collection<? extends GrantedAuthority> authorities) {
        long expirationMs = jwtProperties.getAccessTokenExpirationMinutes() * 60 * 1000; // минуты → миллисекунды

        return JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withSubject(email)
                .withClaim("userId", userId)
                .withClaim("type", "access")
                .withClaim("roles", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMs))
                .sign(accessAlgorithm);
    }

    public String generateRefreshToken(String email, Long userId, UUID jti) {
        long expirationMs = jwtProperties.getRefreshTokenExpirationDays() * 24L * 60 * 60 * 1000;
        return JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withSubject(email)
                .withClaim("userId", userId)
                .withClaim("type", "refresh")
                .withJWTId(jti.toString())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expirationMs))
                .sign(refreshAlgorithm);
    }

    public UUID extractJtiFromRefresh(String token) {
        return UUID.fromString(refreshVerifier.verify(token).getId());
    }

    public Date extractExpiresAtFromRefresh(String token) {
        return refreshVerifier.verify(token).getExpiresAt();
    }

    public boolean validateAccessToken(String token) {
        try {
            accessVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            refreshVerifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String extractUsernameFromAccessToken(String token) {
        return accessVerifier.verify(token).getSubject();
    }

    public String extractUsernameFromRefreshToken(String token) {
        return refreshVerifier.verify(token).getSubject();
    }

    public Long extractUserId(String token) {
        return accessVerifier.verify(token).getClaim("userId").asLong();
    }
}

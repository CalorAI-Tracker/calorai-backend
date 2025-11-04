package ru.calorai.jwToken;

import org.springframework.stereotype.Component;

@Component
public class TokenHasher {
    public String hash(String token) {
        try {
            var md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(token.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            return java.util.Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new IllegalStateException("Cannot hash token", e);
        }
    }
    public boolean matches(String presentedToken, String storedHash) {
        return hash(presentedToken).equals(storedHash);
    }
}

package ru.calorai.dto.response;

public record TokenResponse(
        Long id,
        String accessToken,
        String refreshToken,
        String tokenType,
        long expiresInSeconds
) {}

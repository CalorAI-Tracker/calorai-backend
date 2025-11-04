package ru.calorai.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        long   expiresInSeconds
) {}

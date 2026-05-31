package ru.calorai.dto.request;

public record GoogleAuthRequest(
        String idToken,
        String deviceId
) {}

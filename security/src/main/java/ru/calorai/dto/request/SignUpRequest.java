package ru.calorai.dto.request;

public record SignUpRequest(
    String email,
    String password
) {}

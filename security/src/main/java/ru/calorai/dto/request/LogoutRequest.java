package ru.calorai.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
        @Schema(description = "Refresh токен", example = "eyJhbGciOi...")
        @NotBlank
        String refreshToken
) {}
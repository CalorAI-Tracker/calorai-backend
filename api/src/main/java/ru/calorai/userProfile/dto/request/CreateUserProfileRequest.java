package ru.calorai.userProfile.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateUserProfileRequest(
        @NotNull Long userId,
        @NotNull String sex,

        @NotNull Long height,
        @NotNull Long weight,

        @JsonFormat(pattern = "dd.MM.yyyy", locale = "ru_RU")
        @NotNull LocalDate birthDay,

        @NotNull Integer activityId,
        @NotNull Integer goalId
) {}

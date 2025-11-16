package ru.calorai.healthProfile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record  UserHealthProfileDTO(
        @NotNull Long userId,
        @NotNull String userName,
        @NotNull String userEmail,
        @NotNull String sex,

        @NotNull Long height,
        @NotNull Long weight,

        @NotNull
        @JsonFormat(pattern = "dd.MM.yyyy", locale = "ru_RU")
        LocalDate birthDay,
        Long targetWeightKg,

        Long weeklyRateKg,

        @NotNull String activityCode,
        @NotNull String healthGoalCode
) {}

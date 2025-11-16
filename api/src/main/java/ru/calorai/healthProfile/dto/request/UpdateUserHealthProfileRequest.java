package ru.calorai.healthProfile.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateUserHealthProfileRequest (
        @NotNull String sex,
        @NotNull Long height,
        @NotNull Long weight,
        @JsonFormat(pattern = "dd.MM.yyyy", locale = "ru_RU")
        @NotNull LocalDate birthDay,
        @NotNull
        String activityCode,
        @NotNull
        String healthGoalCode,
        Long targetWeightKg,
        Long weeklyRateKg
) {}
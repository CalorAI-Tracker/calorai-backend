package ru.calorai.healthProfile.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserHealthProfileDTO(
        @NotNull Long userId,
        @NotNull String sex,

        @NotNull Long height,
        @NotNull Long weight,

        @NotNull
        @JsonFormat(pattern = "dd.MM.yyyy", locale = "ru_RU")
        LocalDate birthDay,

        @NotNull Integer activityId,
        @NotNull Integer goalId
) {}

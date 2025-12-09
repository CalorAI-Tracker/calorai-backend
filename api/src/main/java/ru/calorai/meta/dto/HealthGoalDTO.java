package ru.calorai.meta.dto;

public record HealthGoalDTO(
        Short id,
        String code,
        String title,
        String description,
        Integer calorieDeltaPercent
) {
}


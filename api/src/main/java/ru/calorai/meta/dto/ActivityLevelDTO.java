package ru.calorai.meta.dto;

import java.math.BigDecimal;

public record ActivityLevelDTO(
        Short id,
        String code,
        String title,
        String description,
        BigDecimal factor
) {
}


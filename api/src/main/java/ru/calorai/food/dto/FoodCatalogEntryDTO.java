package ru.calorai.food.dto;

import java.math.BigDecimal;

public record FoodCatalogEntryDTO(
        Long id,
        String name,
        String brand,
        String barcode,
        String provider,
        BigDecimal kcalPer100g,
        BigDecimal proteinPer100g,
        BigDecimal fatPer100g,
        BigDecimal carbsPer100g
) {}
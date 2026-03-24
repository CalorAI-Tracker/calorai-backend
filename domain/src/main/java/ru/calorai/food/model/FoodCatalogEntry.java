package ru.calorai.food.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FoodCatalogEntry {

    private Long id;
    private String name;
    private String brand;
    private String barcode;
    private String provider;
    private String providerFoodId;
    private Long createdBy;
    private BigDecimal kcalPer100g;
    private BigDecimal proteinPer100g;
    private BigDecimal fatPer100g;
    private BigDecimal carbsPer100g;
    private BigDecimal fiberPer100g;
    private BigDecimal sugarPer100g;
    private BigDecimal sodiumPer100g;
}

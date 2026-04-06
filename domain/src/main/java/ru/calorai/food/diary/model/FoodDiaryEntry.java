package ru.calorai.food.diary.model;

import lombok.Builder;

import java.time.LocalDate;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FoodDiaryEntry {

    private Long id;
    private Long userId;
    private Long foodCatalogId;

    private LocalDate eatenAt;

    private String meal;

    private BigDecimal quantityGrams;

    private String note;
}
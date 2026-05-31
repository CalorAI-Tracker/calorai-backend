package ru.calorai.food.diary.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDiaryEntryWithNutrition {

    private Long id;
    private Long userId;
    private Long foodCatalogId;
    private String entryName;
    private String brand;
    private LocalDate eatenAt;
    private String meal;
    private BigDecimal quantityGrams;
    private String note;

    // КБЖУ уже пересчитанное для порции
    private Integer kcal;
    private BigDecimal proteinG;
    private BigDecimal fatG;
    private BigDecimal carbsG;
}

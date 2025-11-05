package ru.calorai.foodDiary.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MealIntake {
    private String meal;
    private Integer kcal;
    private BigDecimal proteinG;
    private BigDecimal fatG;
    private BigDecimal carbsG;
    private Integer entriesCnt;
}

package ru.calorai.dailyNutririon.model;

import lombok.*;
import ru.calorai.food.EMeal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DailyMealComposition {
    private LocalDate date;
    private Map<EMeal, List<MealItem>> meals;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MealItem {
        private Long id;
        private String entryName;
        private BigDecimal quantityGrams;
        private Macro macros;
    }
}

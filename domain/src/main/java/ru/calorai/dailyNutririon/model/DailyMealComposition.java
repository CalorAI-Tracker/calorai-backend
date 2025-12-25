package ru.calorai.dailyNutririon.model;

import lombok.*;
import ru.calorai.foodDiary.model.EMeal;

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
    private Map<EMeal, List<MealItem>> meals; // "BREAKFAST" -> [продукты]

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MealItem {
        private String entryName;        // "креветки"
        private BigDecimal quantityGrams; // 100
        private Macro macros;            // КБЖУ через Macro [file:43]
    }
}

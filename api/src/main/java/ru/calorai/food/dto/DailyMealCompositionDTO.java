package ru.calorai.food.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DailyMealCompositionDTO {
    private LocalDate date;
    private Map<String, List<MealProductDTO>> meals;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class MealProductDTO {
        private Long id;
        private String entryName;
        private BigDecimal quantityGrams;
        private Integer kcal;
        private Double proteinG;
        private Double fatG;
        private Double carbsG;
    }
}

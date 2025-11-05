package ru.calorai.foodDiary.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DailyMealIntakeDTO {

    private LocalDate date;
    private List<Item> meals;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder(toBuilder = true)
    public static class Item {
        private String meal;
        private Integer kcal;
        private String proteinG;
        private String fatG;
        private String carbsG;
        private Integer entriesCnt;
    }
}

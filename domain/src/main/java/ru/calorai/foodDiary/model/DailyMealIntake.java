package ru.calorai.foodDiary.model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DailyMealIntake {
    private Long userId;
    private LocalDate date;
    private List<MealIntake> items;
}

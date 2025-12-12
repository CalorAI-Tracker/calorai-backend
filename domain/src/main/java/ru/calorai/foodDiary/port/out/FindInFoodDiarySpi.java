package ru.calorai.foodDiary.port.out;

import ru.calorai.foodDiary.model.DailyMealIntake;
import ru.calorai.foodDiary.model.FoodDiaryEntry;

import java.time.LocalDate;
import java.util.List;

public interface FindInFoodDiarySpi {
    DailyMealIntake findByUserAndDate(Long userId, LocalDate date);

    List<FoodDiaryEntry> findByUserIdAndEatenAt(Long userId, LocalDate eatenAt);
}
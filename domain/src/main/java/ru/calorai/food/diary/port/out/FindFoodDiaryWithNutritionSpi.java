package ru.calorai.food.diary.port.out;

import ru.calorai.food.diary.model.FoodDiaryEntryWithNutrition;

import java.time.LocalDate;
import java.util.List;

public interface FindFoodDiaryWithNutritionSpi {
    List<FoodDiaryEntryWithNutrition> findWithNutritionByUserIdAndEatenAt(Long userId, LocalDate date);
}

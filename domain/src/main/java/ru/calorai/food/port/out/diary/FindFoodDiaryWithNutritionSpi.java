package ru.calorai.food.port.out.diary;

import ru.calorai.food.model.FoodDiaryEntryWithNutrition;

import java.time.LocalDate;
import java.util.List;

public interface FindFoodDiaryWithNutritionSpi {
    List<FoodDiaryEntryWithNutrition> findWithNutritionByUserIdAndEatenAt(Long userId, LocalDate date);
}

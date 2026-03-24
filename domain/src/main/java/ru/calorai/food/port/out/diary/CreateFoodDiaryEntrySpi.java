package ru.calorai.food.port.out.diary;

import ru.calorai.food.model.FoodDiaryEntry;

@FunctionalInterface
public interface CreateFoodDiaryEntrySpi {
    Long createFoodDiaryEntry(FoodDiaryEntry foodDiaryEntry);
}
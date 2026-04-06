package ru.calorai.food.diary.port.out;

import ru.calorai.food.diary.model.FoodDiaryEntry;

@FunctionalInterface
public interface CreateFoodDiaryEntrySpi {
    Long createFoodDiaryEntry(FoodDiaryEntry foodDiaryEntry);
}
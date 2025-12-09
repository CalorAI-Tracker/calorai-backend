package ru.calorai.foodDiary.port.out;

import ru.calorai.foodDiary.model.FoodDiaryEntry;

@FunctionalInterface
public interface CreateFoodDiaryEntrySpi {
    Long createFoodDiaryEntry(FoodDiaryEntry foodDiaryEntry);
}
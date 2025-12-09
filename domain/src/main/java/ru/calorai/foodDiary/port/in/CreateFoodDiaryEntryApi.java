package ru.calorai.foodDiary.port.in;

import ru.calorai.foodDiary.model.FoodDiaryEntry;

@FunctionalInterface
public interface CreateFoodDiaryEntryApi {
    Long createFoodDiaryEntry(FoodDiaryEntry foodDiaryEntry);
}

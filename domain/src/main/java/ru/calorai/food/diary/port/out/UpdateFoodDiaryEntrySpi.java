package ru.calorai.food.diary.port.out;

import ru.calorai.food.diary.model.FoodDiaryEntry;

public interface UpdateFoodDiaryEntrySpi {

    void updateFoodDiaryEntry(FoodDiaryEntry entry);
}

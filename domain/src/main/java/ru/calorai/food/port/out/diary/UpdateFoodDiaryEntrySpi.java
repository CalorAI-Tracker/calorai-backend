package ru.calorai.food.port.out.diary;

import ru.calorai.food.model.FoodDiaryEntry;

public interface UpdateFoodDiaryEntrySpi {

    void updateFoodDiaryEntry(FoodDiaryEntry entry);
}

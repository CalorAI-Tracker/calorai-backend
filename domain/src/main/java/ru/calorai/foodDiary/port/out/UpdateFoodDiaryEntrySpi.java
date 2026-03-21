package ru.calorai.foodDiary.port.out;

import ru.calorai.foodDiary.model.FoodDiaryEntry;

public interface UpdateFoodDiaryEntrySpi {

    void updateFoodDiaryEntry(FoodDiaryEntry entry);
}

package ru.calorai.foodDiary.port.in;

import ru.calorai.foodDiary.model.FoodDiaryEntry;

public interface UpdateFoodDiaryEntryApi {

    void updateFoodDiaryEntry(Long entryId, FoodDiaryEntry incoming);
}

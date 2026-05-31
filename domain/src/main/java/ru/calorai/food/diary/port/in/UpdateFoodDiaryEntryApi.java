package ru.calorai.food.diary.port.in;

import ru.calorai.food.diary.model.cmd.UpdateFoodDiaryEntryCommand;

public interface UpdateFoodDiaryEntryApi {
    void updateFoodDiaryEntry(UpdateFoodDiaryEntryCommand command);
}

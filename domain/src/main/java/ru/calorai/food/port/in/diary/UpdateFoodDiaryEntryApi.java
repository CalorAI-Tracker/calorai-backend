package ru.calorai.food.port.in.diary;

import ru.calorai.food.model.cmd.UpdateFoodDiaryEntryCommand;

public interface UpdateFoodDiaryEntryApi {
    void updateFoodDiaryEntry(UpdateFoodDiaryEntryCommand command);
}

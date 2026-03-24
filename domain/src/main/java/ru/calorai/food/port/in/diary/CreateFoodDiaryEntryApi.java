package ru.calorai.food.port.in.diary;

import ru.calorai.food.model.cmd.CreateFoodDiaryEntryCommand;

@FunctionalInterface
public interface CreateFoodDiaryEntryApi {
    Long createFoodDiaryEntry(CreateFoodDiaryEntryCommand command);
}

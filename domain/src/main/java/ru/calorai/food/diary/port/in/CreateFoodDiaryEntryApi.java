package ru.calorai.food.diary.port.in;

import ru.calorai.food.diary.model.cmd.CreateFoodDiaryEntryCommand;

@FunctionalInterface
public interface CreateFoodDiaryEntryApi {
    Long createFoodDiaryEntry(CreateFoodDiaryEntryCommand command);
}

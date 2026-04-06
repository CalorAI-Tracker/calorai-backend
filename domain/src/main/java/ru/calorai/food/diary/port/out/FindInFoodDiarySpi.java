package ru.calorai.food.diary.port.out;

import ru.calorai.food.diary.model.FoodDiaryEntry;

import java.util.Optional;

public interface FindInFoodDiarySpi {
    Optional<FoodDiaryEntry> findByIdAndUserId(Long entryId, Long userId);
}
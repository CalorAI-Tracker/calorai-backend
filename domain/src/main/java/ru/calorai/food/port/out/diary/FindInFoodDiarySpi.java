package ru.calorai.food.port.out.diary;

import ru.calorai.food.model.FoodDiaryEntry;

import java.util.Optional;

public interface FindInFoodDiarySpi {
    Optional<FoodDiaryEntry> findByIdAndUserId(Long entryId, Long userId);
}
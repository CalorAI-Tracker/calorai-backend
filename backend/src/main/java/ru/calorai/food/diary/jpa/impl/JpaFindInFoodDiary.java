package ru.calorai.food.diary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.food.diary.jpa.mapper.FoodDiaryEntityMapper;
import ru.calorai.food.diary.jpa.repository.FoodDiaryRepository;
import ru.calorai.food.diary.model.FoodDiaryEntry;
import ru.calorai.food.diary.port.out.FindInFoodDiarySpi;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaFindInFoodDiary implements FindInFoodDiarySpi {

    private final FoodDiaryRepository foodDiaryRepository;
    private final FoodDiaryEntityMapper foodDiaryEntityMapper;

    @Override
    public Optional<FoodDiaryEntry> findByIdAndUserId(Long entryId, Long userId) {
        return foodDiaryRepository.findByIdAndUserId(entryId, userId)
                .map(foodDiaryEntityMapper::toDomain);
    }
}

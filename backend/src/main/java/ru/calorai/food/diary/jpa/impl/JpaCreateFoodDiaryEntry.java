package ru.calorai.food.diary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.food.diary.jpa.mapper.FoodDiaryEntityMapper;
import ru.calorai.food.diary.jpa.repository.FoodDiaryRepository;
import ru.calorai.food.diary.model.FoodDiaryEntry;
import ru.calorai.food.diary.port.out.CreateFoodDiaryEntrySpi;

@Component
@RequiredArgsConstructor
public class JpaCreateFoodDiaryEntry implements CreateFoodDiaryEntrySpi {

    private final FoodDiaryRepository foodDiaryRepository;
    private final FoodDiaryEntityMapper foodDiaryEntityMapper;

    @Override
    public Long createFoodDiaryEntry(FoodDiaryEntry entry) {
        var entity = foodDiaryEntityMapper.toEntity(entry);
        return foodDiaryRepository.save(entity).getId();
    }
}

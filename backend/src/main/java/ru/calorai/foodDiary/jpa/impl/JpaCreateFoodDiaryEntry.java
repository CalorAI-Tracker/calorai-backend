package ru.calorai.foodDiary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.foodDiary.jpa.mapper.FoodDiaryEntityMapper;
import ru.calorai.foodDiary.jpa.repository.FoodDiaryRepository;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.port.out.CreateFoodDiaryEntrySpi;

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

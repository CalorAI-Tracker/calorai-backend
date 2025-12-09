package ru.calorai.foodDiary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.foodDiary.jpa.mapper.FoodDiaryMapper;
import ru.calorai.foodDiary.jpa.repository.FoodDiaryRepository;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.port.out.CreateFoodDiaryEntrySpi;

@Component
@RequiredArgsConstructor
public class JpaCreateFoodDiaryEntry implements CreateFoodDiaryEntrySpi {

    private final FoodDiaryRepository foodDiaryRepository;
    private final FoodDiaryMapper foodDiaryMapper;

    @Override
    public Long createFoodDiaryEntry(FoodDiaryEntry entry) {
        var entity = foodDiaryMapper.toEntity(entry);
        return foodDiaryRepository.save(entity).getId();
    }
}

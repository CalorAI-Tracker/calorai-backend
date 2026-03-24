package ru.calorai.food.diary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.food.exception.FoodDiaryEntryNotFoundException;
import ru.calorai.food.diary.jpa.mapper.FoodDiaryEntityMapper;
import ru.calorai.food.diary.jpa.repository.FoodDiaryRepository;
import ru.calorai.food.model.FoodDiaryEntry;
import ru.calorai.food.port.out.diary.UpdateFoodDiaryEntrySpi;

@Component
@RequiredArgsConstructor
public class JpaUpdateFoodDiaryEntry implements UpdateFoodDiaryEntrySpi {

    private final FoodDiaryRepository foodDiaryRepository;
    private final FoodDiaryEntityMapper foodDiaryEntityMapper;

    @Override
    public void updateFoodDiaryEntry(FoodDiaryEntry entry) {
        var entity = foodDiaryRepository.findByIdAndUserId(entry.getId(), entry.getUserId())
                .orElseThrow(() -> new FoodDiaryEntryNotFoundException(entry.getUserId(), entry.getId()));
        foodDiaryEntityMapper.copyToEntity(entry, entity);
        foodDiaryRepository.save(entity);
    }
}

package ru.calorai.foodDiary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.foodDiary.exception.FoodDiaryEntryNotFoundException;
import ru.calorai.foodDiary.jpa.mapper.FoodDiaryEntityMapper;
import ru.calorai.foodDiary.jpa.repository.FoodDiaryRepository;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.port.out.UpdateFoodDiaryEntrySpi;

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

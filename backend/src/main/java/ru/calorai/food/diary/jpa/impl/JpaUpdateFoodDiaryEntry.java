package ru.calorai.food.diary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.food.diary.exception.FoodDiaryEntryNotFoundException;
import ru.calorai.food.diary.jpa.repository.FoodDiaryRepository;
import ru.calorai.food.diary.model.FoodDiaryEntry;
import ru.calorai.food.diary.port.out.UpdateFoodDiaryEntrySpi;

@Component
@RequiredArgsConstructor
public class JpaUpdateFoodDiaryEntry implements UpdateFoodDiaryEntrySpi {

    private final FoodDiaryRepository foodDiaryRepository;

    @Override
    public void updateFoodDiaryEntry(FoodDiaryEntry entry) {
        var entity = foodDiaryRepository.findByIdAndUserId(entry.getId(), entry.getUserId())
                .orElseThrow(() -> new FoodDiaryEntryNotFoundException(entry.getUserId(), entry.getId()));

        entity.setQuantityGrams(entry.getQuantityGrams());

        foodDiaryRepository.save(entity);
    }
}

package ru.calorai.foodDiary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.foodDiary.jpa.mapper.FoodDiaryMapper;
import ru.calorai.foodDiary.jpa.repository.FoodDiaryRepository;
import ru.calorai.foodDiary.model.DailyMealIntake;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.model.MealIntake;
import ru.calorai.foodDiary.port.out.FindInFoodDiarySpi;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaFindInFoodDiary implements FindInFoodDiarySpi {

    private final FoodDiaryRepository foodDiaryRepository;
    private final FoodDiaryMapper foodDiaryMapper;

    @Override
    public DailyMealIntake findByUserAndDate(Long userId, LocalDate date) {
        var rows = foodDiaryRepository.aggregateByMeal(userId, date);

        var items = rows.stream().map(r ->
                MealIntake.builder()
                        .meal(r.getMeal())
                        .kcal(r.getKcal())
                        .proteinG(nz(r.getProteinG()))
                        .fatG(nz(r.getFatG()))
                        .carbsG(nz(r.getCarbsG()))
                        .entriesCnt(r.getEntriesCnt())
                        .build()
        ).toList();

        return DailyMealIntake.builder()
                .userId(userId)
                .date(date)
                .items(items)
                .build();
    }

    @Override
    public List<FoodDiaryEntry> findByUserIdAndEatenAt(Long userId, LocalDate eatenAt) {
        return foodDiaryRepository.findByUserIdAndEatenAt(userId, eatenAt)
                .stream()
                .map(foodDiaryMapper::toDomain)
                .toList();
    }

    private BigDecimal nz(BigDecimal v) {
        return v == null ? new BigDecimal("0.00") : v.setScale(2, RoundingMode.HALF_UP);
    }
}

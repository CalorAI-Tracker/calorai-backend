package ru.calorai.foodDiary.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.dailyNutririon.model.DailyMealComposition;
import ru.calorai.dailyNutririon.port.out.FindMealCompositionSpi;
import ru.calorai.foodDiary.jpa.mapper.FoodDiaryEntityMapper;
import ru.calorai.foodDiary.jpa.repository.FoodDiaryRepository;
import ru.calorai.foodDiary.model.DailyMealIntake;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.model.MealIntake;
import ru.calorai.foodDiary.port.out.FindInFoodDiarySpi;

import java.time.LocalDate;
import java.util.List;

import static ru.calorai.foodDiary.jpa.mapper.FoodDiaryEntityMapper.nz;

@Component
@RequiredArgsConstructor
public class JpaFindInFoodDiary implements FindInFoodDiarySpi, FindMealCompositionSpi {

    private final FoodDiaryRepository foodDiaryRepository;
    private final FoodDiaryEntityMapper foodDiaryEntityMapper;

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
                .map(foodDiaryEntityMapper::toDomain)
                .toList();
    }

    @Override
    public DailyMealComposition findMealCompositionByUserAndDate(Long userId, LocalDate date) {
        var entries = foodDiaryRepository.findByUserIdAndEatenAt(userId, date);

        var groupedMeals = foodDiaryEntityMapper.entriesToGroupedMealItems(entries);

        return DailyMealComposition.builder()
                .date(date)
                .meals(groupedMeals)
                .build();
    }
}

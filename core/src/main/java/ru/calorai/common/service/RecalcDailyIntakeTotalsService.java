package ru.calorai.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.dailyNutririon.port.out.UpsertDailyIntakeTotalsSpi;
import ru.calorai.food.model.FoodDiaryEntry;
import ru.calorai.food.port.out.diary.FindFoodDiaryWithNutritionSpi;
import ru.calorai.food.port.out.diary.FindInFoodDiarySpi;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RecalcDailyIntakeTotalsService {

    private final FindFoodDiaryWithNutritionSpi findFoodDiaryWithNutritionSpi;
    private final UpsertDailyIntakeTotalsSpi upsertDailyIntakeTotalsSpi;

    @Transactional
    public void recalcForUserAndDate(Long userId, LocalDate date) {
        var entries = findFoodDiaryWithNutritionSpi
                .findWithNutritionByUserIdAndEatenAt(userId, date);

        int kcal = 0;
        BigDecimal protein = BigDecimal.ZERO;
        BigDecimal fat = BigDecimal.ZERO;
        BigDecimal carbs = BigDecimal.ZERO;

        for (var e : entries) {
            if (e.getKcal() != null) kcal += e.getKcal();
            if (e.getProteinG() != null) protein = protein.add(e.getProteinG());
            if (e.getFatG() != null) fat = fat.add(e.getFatG());
            if (e.getCarbsG() != null) carbs = carbs.add(e.getCarbsG());
        }

        var actual = Macro.builder()
                .kcal(kcal)
                .proteinG(protein)
                .fatG(fat)
                .carbsG(carbs)
                .build();

        upsertDailyIntakeTotalsSpi.upsertDailyIntakeTotals(userId, date, actual, entries.size());
    }
}

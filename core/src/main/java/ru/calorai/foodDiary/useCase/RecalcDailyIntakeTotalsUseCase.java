package ru.calorai.foodDiary.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.dailyNutririon.port.out.UpsertDailyIntakeTotalsSpi;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.port.out.FindInFoodDiarySpi;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RecalcDailyIntakeTotalsUseCase {

    private final FindInFoodDiarySpi findInFoodDiarySpi;
    private final UpsertDailyIntakeTotalsSpi upsertDailyIntakeTotalsSpi;

    @Transactional
    public void recalcForUserAndDate(Long userId, LocalDate date) {
        var entries = findInFoodDiarySpi.findByUserIdAndEatenAt(userId, date);

        int kcal = 0;
        BigDecimal protein = BigDecimal.ZERO;
        BigDecimal fat = BigDecimal.ZERO;
        BigDecimal carbs = BigDecimal.ZERO;

        for (FoodDiaryEntry e : entries) {
            if (e.getKcal() != null) {
                kcal += e.getKcal();
            }
            if (e.getProteinG() != null) {
                protein = protein.add(BigDecimal.valueOf(e.getProteinG()));
            }
            if (e.getFatG() != null) {
                fat = fat.add(BigDecimal.valueOf(e.getFatG()));
            }
            if (e.getCarbsG() != null) {
                carbs = carbs.add(BigDecimal.valueOf(e.getCarbsG()));
            }
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

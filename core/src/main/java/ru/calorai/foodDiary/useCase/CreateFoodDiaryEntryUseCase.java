package ru.calorai.foodDiary.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.port.in.CreateFoodDiaryEntryApi;
import ru.calorai.foodDiary.port.out.CreateFoodDiaryEntrySpi;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateFoodDiaryEntryUseCase implements CreateFoodDiaryEntryApi {

    private final CreateFoodDiaryEntrySpi createFoodDiaryEntrySpi;
    private final RecalcDailyIntakeTotalsUseCase recalcDailyIntakeTotalsUseCase;

    @Override
    @Transactional
    public Long createFoodDiaryEntry(FoodDiaryEntry foodDiaryEntry) {
        var eatenAt = foodDiaryEntry.getEatenAt() == null ? LocalDate.now() : foodDiaryEntry.getEatenAt();
        var entry = foodDiaryEntry.toBuilder()
                .kcal(calcKcal(
                        foodDiaryEntry.getProteinG(),
                        foodDiaryEntry.getFatG(),
                        foodDiaryEntry.getCarbsG())
                )
                .eatenAt(eatenAt)
                .build();

        var id = createFoodDiaryEntrySpi.createFoodDiaryEntry(entry);

        recalcDailyIntakeTotalsUseCase.recalcForUserAndDate(entry.getUserId(), eatenAt);

        return id;
    }

    private int calcKcal(Double p, Double f, Double c) {
        if (p == null || f == null || c == null) return 0;
        double kcal = p * 4 + f * 9 + c * 4;
        return (int) Math.round(kcal);
    }
}

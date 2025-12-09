package ru.calorai.foodDiary.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.port.in.CreateFoodDiaryEntryApi;
import ru.calorai.foodDiary.port.out.CreateFoodDiaryEntrySpi;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CreateFoodDiaryEntryUseCase implements CreateFoodDiaryEntryApi {

    private final CreateFoodDiaryEntrySpi createFoodDiaryEntrySpi;

    @Override
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

        return createFoodDiaryEntrySpi.createFoodDiaryEntry(entry);
    }

    private int calcKcal(Double p, Double f, Double c) {
        if (p == null || f == null || c == null) return 0;
        double kcal = p * 4 + f * 9 + c * 4;
        return (int) Math.round(kcal);
    }
}

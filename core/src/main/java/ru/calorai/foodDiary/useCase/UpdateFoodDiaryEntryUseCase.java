package ru.calorai.foodDiary.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.foodDiary.exception.FoodDiaryEntryNotFoundException;
import ru.calorai.foodDiary.model.FoodDiaryEntry;
import ru.calorai.foodDiary.port.in.UpdateFoodDiaryEntryApi;
import ru.calorai.foodDiary.port.out.FindInFoodDiarySpi;
import ru.calorai.foodDiary.port.out.UpdateFoodDiaryEntrySpi;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UpdateFoodDiaryEntryUseCase implements UpdateFoodDiaryEntryApi {

    private final FindInFoodDiarySpi findInFoodDiarySpi;
    private final UpdateFoodDiaryEntrySpi updateFoodDiaryEntrySpi;
    private final RecalcDailyIntakeTotalsUseCase recalcDailyIntakeTotalsUseCase;

    @Override
    @Transactional
    public void updateFoodDiaryEntry(Long userId, Long entryId, FoodDiaryEntry incoming) {
        var existing = findInFoodDiarySpi.findByIdAndUserId(entryId, userId)
                .orElseThrow(() -> new FoodDiaryEntryNotFoundException(userId, entryId));

        var eatenAt = incoming.getEatenAt() != null ? incoming.getEatenAt() : existing.getEatenAt();
        if (eatenAt == null) {
            eatenAt = LocalDate.now();
        }

        var previousDate = existing.getEatenAt();

        var merged = existing.toBuilder()
                .entryName(incoming.getEntryName())
                .meal(incoming.getMeal())
                .eatenAt(eatenAt)
                .quantityGrams(incoming.getQuantityGrams())
                .proteinG(incoming.getProteinG())
                .fatG(incoming.getFatG())
                .carbsG(incoming.getCarbsG())
                .kcal(calcKcal(incoming.getProteinG(), incoming.getFatG(), incoming.getCarbsG()))
                .brand(incoming.getBrand())
                .barcode(incoming.getBarcode())
                .note(incoming.getNote())
                .build();

        updateFoodDiaryEntrySpi.updateFoodDiaryEntry(merged);

        recalcDailyIntakeTotalsUseCase.recalcForUserAndDate(userId, eatenAt);
        if (previousDate != null && !previousDate.equals(eatenAt)) {
            recalcDailyIntakeTotalsUseCase.recalcForUserAndDate(userId, previousDate);
        }
    }

    private int calcKcal(Double p, Double f, Double c) {
        if (p == null || f == null || c == null) return 0;
        double kcal = p * 4 + f * 9 + c * 4;
        return (int) Math.round(kcal);
    }
}

package ru.calorai.food.diary.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.service.RecalcDailyIntakeTotalsService;
import ru.calorai.food.diary.exception.FoodDiaryEntryNotFoundException;
import ru.calorai.food.diary.model.cmd.UpdateFoodDiaryEntryCommand;
import ru.calorai.food.diary.port.in.UpdateFoodDiaryEntryApi;
import ru.calorai.food.diary.port.out.FindInFoodDiarySpi;
import ru.calorai.food.diary.port.out.UpdateFoodDiaryEntrySpi;
import ru.calorai.security.port.in.CurrentUserExtractorApi;

@Service
@RequiredArgsConstructor
public class UpdateFoodDiaryEntryUseCase implements UpdateFoodDiaryEntryApi {

    private final FindInFoodDiarySpi findInFoodDiarySpi;
    private final UpdateFoodDiaryEntrySpi updateFoodDiaryEntrySpi;

    private final CurrentUserExtractorApi currentUserExtractor;

    private final RecalcDailyIntakeTotalsService recalcDailyIntakeTotalsService;

    @Override
    @Transactional
    public void updateFoodDiaryEntry(UpdateFoodDiaryEntryCommand command) {
        Long userId = currentUserExtractor.getUser().getId();
        var existing = findInFoodDiarySpi.findByIdAndUserId(command.getEntryId(), userId)
                .orElseThrow(() -> new FoodDiaryEntryNotFoundException(userId, command.getEntryId()));

        var merged = existing.toBuilder()
                .quantityGrams(command.getPortionQuantityGrams())
                .build();

        updateFoodDiaryEntrySpi.updateFoodDiaryEntry(merged);

        recalcDailyIntakeTotalsService.recalcForUserAndDate(userId, existing.getEatenAt());
    }
}

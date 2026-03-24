package ru.calorai.foodDiary.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.service.RecalcDailyIntakeTotalsService;
import ru.calorai.foodDiary.exception.FoodDiaryEntryNotFoundException;
import ru.calorai.foodDiary.port.in.DeleteFoodDiaryEntryApi;
import ru.calorai.foodDiary.port.out.DeleteFoodDiaryEntrySpi;
import ru.calorai.foodDiary.port.out.FindInFoodDiarySpi;
import ru.calorai.security.port.in.CurrentUserExtractorApi;

@Service
@RequiredArgsConstructor
public class DeleteFoodDiaryEntryUseCase implements DeleteFoodDiaryEntryApi {

    private final FindInFoodDiarySpi findInFoodDiarySpi;
    private final DeleteFoodDiaryEntrySpi deleteFoodDiaryEntrySpi;

    private final CurrentUserExtractorApi currentUserExtractor;

    private final RecalcDailyIntakeTotalsService recalcDailyIntakeTotalsService;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void deleteFoodDiaryEntry(Long entryId) {
        Long userId = currentUserExtractor.getUser().getId();
        var existing = findInFoodDiarySpi.findByIdAndUserId(entryId, userId)
                .orElseThrow(() -> new FoodDiaryEntryNotFoundException(userId, entryId));

        var eatenAt = existing.getEatenAt();

        deleteFoodDiaryEntrySpi.deleteByIdAndUserId(entryId, userId);

        // пересчитываем итоги за день, из которого удалили запись
        recalcDailyIntakeTotalsService.recalcForUserAndDate(userId, eatenAt);
    }
}

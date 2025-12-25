package ru.calorai.dailyNutrition.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.dailyNutririon.model.DailyMealComposition;
import ru.calorai.dailyNutririon.port.in.FindDailyMealCompositionApi;
import ru.calorai.dailyNutririon.port.out.FindMealCompositionSpi;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FindDailyMealCompositionUseCase implements FindDailyMealCompositionApi {

    private final FindMealCompositionSpi findMealCompositionSpi;

    @Override
    @Transactional(readOnly = true)
    public DailyMealComposition findByUserAndDate(Long userId, LocalDate date) {
        return findMealCompositionSpi.findMealCompositionByUserAndDate(userId, date);
    }
}

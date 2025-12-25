package ru.calorai.dailyNutrition.useCase;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.dailyNutririon.exception.DailyNutritionNotFoundException;
import ru.calorai.dailyNutririon.exception.DailyNutritionRecalcFailedException;
import ru.calorai.dailyNutririon.model.DailyNutrition;
import ru.calorai.dailyNutririon.port.in.FindDailyNutritionApi;
import ru.calorai.dailyNutririon.port.in.RecalcTodayTargetsApi;
import ru.calorai.dailyNutririon.port.out.FindDailyNutritionSpi;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FindDailyNutritionUseCase implements FindDailyNutritionApi {

    private final FindDailyNutritionSpi findDailyNutritionSpi;

    private final RecalcTodayTargetsApi recalcTodayTargetsApi;

    @Override
    @Transactional
    public DailyNutrition findByUserAndDate(Long userId, LocalDate date, boolean ensureTodayTarget) {
        var result = findDailyNutritionSpi.findByUserAndDate(userId, date);
        if (result.isPresent()) return result.get();

        // ленивый пересчёт только для сегодняшней даты
        if (ensureTodayTarget && LocalDate.now().equals(date)) {
            recalcTodayTargetsApi.recalcForToday(userId);
            return findDailyNutritionSpi.findByUserAndDate(userId, date)
                    .orElseThrow(() -> new DailyNutritionRecalcFailedException(userId, date));
        }

        throw new DailyNutritionNotFoundException(userId, date);
    }
}
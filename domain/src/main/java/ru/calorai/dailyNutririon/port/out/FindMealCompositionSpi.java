package ru.calorai.dailyNutririon.port.out;

import ru.calorai.dailyNutririon.model.DailyMealComposition;

import java.time.LocalDate;

public interface FindMealCompositionSpi {
    DailyMealComposition findMealCompositionByUserAndDate(Long userId, LocalDate date);
}

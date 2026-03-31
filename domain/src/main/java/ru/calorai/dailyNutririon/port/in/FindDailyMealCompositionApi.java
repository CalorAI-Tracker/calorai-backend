package ru.calorai.dailyNutririon.port.in;

import ru.calorai.dailyNutririon.model.DailyMealComposition;

import java.time.LocalDate;

public interface FindDailyMealCompositionApi {
    DailyMealComposition findByUserAndDate(LocalDate date);
}

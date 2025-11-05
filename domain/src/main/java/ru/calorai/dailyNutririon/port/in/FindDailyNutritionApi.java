package ru.calorai.dailyNutririon.port.in;

import ru.calorai.dailyNutririon.model.DailyNutrition;

import java.time.LocalDate;

public interface FindDailyNutritionApi {
    DailyNutrition findByUserAndDate(Long userId, LocalDate date, boolean ensureTodayTarget);
}

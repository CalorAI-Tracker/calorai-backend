package ru.calorai.dailyNutririon.port.out;

import ru.calorai.dailyNutririon.model.DailyNutrition;

import java.time.LocalDate;
import java.util.Optional;

public interface FindDailyNutritionSpi {
    Optional<DailyNutrition> findByUserAndDate(Long userId, LocalDate date);
}

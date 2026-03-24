package ru.calorai.food.port.out.diary;

import ru.calorai.food.model.DailyMealIntake;

import java.time.LocalDate;

@FunctionalInterface
public interface FindDailyMealIntakeSpi {
    DailyMealIntake findByUserAndDate(Long userId, LocalDate date);
}

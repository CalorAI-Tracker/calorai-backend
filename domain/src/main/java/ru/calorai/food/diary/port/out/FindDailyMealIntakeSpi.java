package ru.calorai.food.diary.port.out;

import ru.calorai.food.diary.model.DailyMealIntake;

import java.time.LocalDate;

@FunctionalInterface
public interface FindDailyMealIntakeSpi {
    DailyMealIntake findByUserAndDate(Long userId, LocalDate date);
}

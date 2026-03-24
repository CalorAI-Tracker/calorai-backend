package ru.calorai.food.port.in.diary;

import ru.calorai.food.model.DailyMealIntake;

import java.time.LocalDate;

public interface FindDailyMealIntakeApi {
    DailyMealIntake findByUserAndDate(Long userId, LocalDate date);
}

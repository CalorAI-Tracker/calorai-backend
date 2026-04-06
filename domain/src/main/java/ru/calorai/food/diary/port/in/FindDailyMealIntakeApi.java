package ru.calorai.food.diary.port.in;

import ru.calorai.food.diary.model.DailyMealIntake;

import java.time.LocalDate;

public interface FindDailyMealIntakeApi {
    DailyMealIntake findByUserAndDate(LocalDate date);
}

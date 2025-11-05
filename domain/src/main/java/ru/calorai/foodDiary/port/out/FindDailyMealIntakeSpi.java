package ru.calorai.foodDiary.port.out;

import ru.calorai.foodDiary.model.DailyMealIntake;

import java.time.LocalDate;

public interface FindDailyMealIntakeSpi {
    DailyMealIntake findByUserAndDate(Long userId, LocalDate date);
}
package ru.calorai.foodDiary.port.in;

import ru.calorai.foodDiary.model.DailyMealIntake;

import java.time.LocalDate;

public interface FindDailyMealIntakeApi {
    DailyMealIntake findByUserAndDate(Long userId, LocalDate date);
}

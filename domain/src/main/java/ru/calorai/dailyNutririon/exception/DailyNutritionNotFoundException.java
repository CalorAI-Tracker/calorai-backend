package ru.calorai.dailyNutririon.exception;

import java.time.LocalDate;

public class DailyNutritionNotFoundException extends RuntimeException {
    public DailyNutritionNotFoundException(String message) {
        super(message);
    }

    public DailyNutritionNotFoundException(Long userId, LocalDate date) {
        super(String.format("Дневная норма питания не найдена для пользователя=%d и даты=%s", userId, date));
    }
}

package ru.calorai.dailyNutririon.exception;

import java.time.LocalDate;

public class DailyNutritionRecalcFailedException extends RuntimeException {
    public DailyNutritionRecalcFailedException(String message) {
        super(message);
    }

  public DailyNutritionRecalcFailedException(Long userId, LocalDate date) {
    super(String.format("Не удалось рассчитать дневную норму питания для пользователя=%d и даты=%s даже после пересчета", userId, date));
  }
}

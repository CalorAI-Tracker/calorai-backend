package ru.calorai.dailyNutririon.exception;

import java.time.LocalDate;

public class UnsupportedDateOperationException extends RuntimeException {
    public UnsupportedDateOperationException(String message) {
        super(message);
    }

    public UnsupportedDateOperationException(LocalDate date, String operation) {
        super(String.format("Операция '%s' не поддерживается для даты=%s", operation, date));
    }
}

package ru.calorai.profile.exception;

public class UserHealthProfileNotFoundException extends RuntimeException {
    public UserHealthProfileNotFoundException(String message) {
        super(message);
    }

    public UserHealthProfileNotFoundException(Long id) {
        super("Профиль пользователя с идентификатором %d не найден".formatted(id));
    }
}

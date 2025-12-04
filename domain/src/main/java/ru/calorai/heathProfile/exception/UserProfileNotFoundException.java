package ru.calorai.heathProfile.exception;

public class UserProfileNotFoundException extends RuntimeException {
    public UserProfileNotFoundException(String message) {
        super(message);
    }

    public UserProfileNotFoundException(Long id) {
        super("Профиль пользователя с идентификатором %d не найден".formatted(id));
    }
}

package ru.calorai.heathProfile.exception;

public class UserHealthProfileAlreadyExistException extends RuntimeException {
    public UserHealthProfileAlreadyExistException(String message) {
        super(message);
    }

    public UserHealthProfileAlreadyExistException() {
        super("Профиль данного пользователя уже существует");
    }
}

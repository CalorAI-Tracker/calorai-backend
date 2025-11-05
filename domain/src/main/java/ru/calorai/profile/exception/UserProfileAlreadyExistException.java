package ru.calorai.profile.exception;

public class UserProfileAlreadyExistException extends RuntimeException {
    public UserProfileAlreadyExistException(String message) {
        super(message);
    }

    public UserProfileAlreadyExistException() {
        super("Профиль данного пользователя уже существует");
    }
}

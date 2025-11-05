package ru.calorai.users.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }

  public UserNotFoundException(Long id) {
    super("Пользователь с идентификатором %d не найден".formatted(id));
  }
}

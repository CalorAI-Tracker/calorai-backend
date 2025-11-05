package ru.calorai.users.exception;

public class EmailAlreadyTakenException extends RuntimeException {
    public EmailAlreadyTakenException(String message) {
        super(message);
    }

  public EmailAlreadyTakenException() {
    super("Введённый email уже занят другим пользователем");
  }
}

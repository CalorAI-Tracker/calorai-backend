package ru.calorai.healthProfile.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.calorai.common.model.ErrorPresentation;
import ru.calorai.profile.exception.UserHealthProfileAlreadyExistException;
import ru.calorai.profile.exception.UserHealthProfileNotFoundException;

import java.util.Date;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserHealthProfileAdviceController {

    @ExceptionHandler({UserHealthProfileAlreadyExistException.class, UserHealthProfileNotFoundException.class})
    public ErrorPresentation handleUserProfileException(Exception ex, HttpRequest request) {
        return new ErrorPresentation(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getURI().toString()
        );
    }
}

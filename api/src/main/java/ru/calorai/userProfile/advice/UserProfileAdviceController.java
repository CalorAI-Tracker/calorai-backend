package ru.calorai.userProfile.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.calorai.common.model.ErrorPresentation;
import ru.calorai.profile.exception.UserProfileAlreadyExistException;
import ru.calorai.profile.exception.UserProfileNotFoundException;

import java.util.Date;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class UserProfileAdviceController {

    @ExceptionHandler({UserProfileAlreadyExistException.class, UserProfileNotFoundException.class})
    public ErrorPresentation handleUserProfileException(Exception ex, HttpRequest request) {
        return new ErrorPresentation(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                new Date(),
                ex.getMessage(),
                request.getURI().toString()
        );
    }
}

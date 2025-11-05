package ru.calorai.controller.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import ru.calorai.common.model.ErrorPresentation;
import ru.calorai.users.exception.EmailAlreadyTakenException;

import java.util.Date;

@RestController
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityAdviceController {

    @ExceptionHandler(EmailAlreadyTakenException.class)
    public ErrorPresentation handleEmailAlreadyTaken(WebRequest request, EmailAlreadyTakenException ex) {
        return new ErrorPresentation(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
    }
}

package ru.calorai.controller.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
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

//    @ExceptionHandler({AuthenticationException.class, AuthenticationCredentialsNotFoundException.class})
//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    public ResponseEntity<ErrorPresentation> handleUnauthorized(
//            Exception ex,
//            HttpServletRequest request) {
//
//        ErrorPresentation error = new ErrorPresentation(
//                HttpStatus.UNAUTHORIZED.value(),
//                new Date(),
//                ex.getMessage() != null ? ex.getMessage() : "Доступ запрещён",
//                request.getRequestURI()
//        );
//
//        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<ErrorPresentation> handleForbidden(
//            AccessDeniedException ex,
//            HttpServletRequest request) {
//
//        ErrorPresentation error = new ErrorPresentation(
//                HttpStatus.FORBIDDEN.value(),
//                new Date(),
//                ex.getMessage() != null ? ex.getMessage() : "Недостаточно прав доступа",
//                request.getRequestURI()
//        );
//
//        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
//    }
}

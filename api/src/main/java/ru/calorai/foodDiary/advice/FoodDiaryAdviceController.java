package ru.calorai.foodDiary.advice;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.calorai.common.model.ErrorPresentation;
import ru.calorai.foodDiary.exception.FoodDiaryEntryNotFoundException;

import java.util.Date;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class FoodDiaryAdviceController {

    @ExceptionHandler(FoodDiaryEntryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorPresentation handleFoodDiaryEntryNotFound(
            FoodDiaryEntryNotFoundException ex,
            HttpServletRequest request
    ) {
        return new ErrorPresentation(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                request.getRequestURI()
        );
    }
}

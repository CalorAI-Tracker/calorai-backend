package ru.calorai.foodDiary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.calorai.foodDiary.dto.DailyMealIntakeDTO;
import ru.calorai.foodDiary.mapper.DailyMealIntakeDtoMapper;
import ru.calorai.foodDiary.port.in.FindDailyMealIntakeApi;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@RequestMapping("/daily-meal")
@Tag(name = "Daily MealIntake API", description = "API для получения агрегированной статистики КБЖУ по приёмам пищи")
public class DailyMealIntakeController {

    private final FindDailyMealIntakeApi api;
    private final DailyMealIntakeDtoMapper mapper;

    @Operation(
            summary = "Получение агрегированных КБЖУ по приёмам пищи за дату",
            description = """
                    Возвращает сводку потреблённых калорий, белков, жиров и углеводов, 
                    сгруппированных по приёмам пищи (завтрак, обед, ужин, перекус) для указанного пользователя и даты.
                    
                    Данные агрегируются из таблицы food_diary по полю meal (enum: BREAKFAST, LUNCH, DINNER, SNACK).
                    Если дата не указана, используется текущая дата.
                    
                    Поля Item в ответе:
                    - meal: название приёма пищи
                    - kcal: суммарные калории
                    - proteinG, fatG, carbsG: макронутриенты в граммах (округлено до 2 знаков)
                    - entriesCnt: количество записей в приёме пищи
                    """
    )
    @GetMapping("/{userId}")
    public ResponseEntity<DailyMealIntakeDTO> getMeals(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        var d = date != null ? date : LocalDate.now();
        var domain = api.findByUserAndDate(userId, d);
        return ResponseEntity.ok(mapper.toDto(domain));
    }
}

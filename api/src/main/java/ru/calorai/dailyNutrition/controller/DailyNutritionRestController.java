package ru.calorai.dailyNutrition.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.calorai.dailyNutririon.port.in.FindDailyNutritionApi;
import ru.calorai.dailyNutrition.dto.DailyNutritionDTO;
import ru.calorai.dailyNutrition.mapper.DailyNutritionDtoMapper;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily-nutrition")
@Tag(
        name = "Daily Nutrition",
        description = """
        API для получения итоговой статистики КБЖУ за день.
        
        Сравнивает плановые цели (user_daily_targets) с фактическим потреблением (daily_intake_totals).
        Возвращает план, факт, остаток и проценты выполнения для дашборда.
        
        План рассчитывается по формуле Mifflin-St Jeor с учётом user_profile (возраст, вес, пол, активность).
        """
)
public class DailyNutritionRestController {

    private final FindDailyNutritionApi findDailyNutritionApi;
    private final DailyNutritionDtoMapper mapper;

    @Operation(
            summary = "Получить КБЖУ на дату",
            description = "Возвращает план, факт, остаток и проценты выполнения"
    )
    @GetMapping("/{userId}")
    public ResponseEntity<DailyNutritionDTO> getDaily(
            @PathVariable("userId") Long userId,
            @Parameter(description = "Дата в формате ISO", example = "2025-11-05")
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        var d = (date != null) ? date : LocalDate.now();
        return ResponseEntity.ok(mapper.toDto(
                findDailyNutritionApi.findByUserAndDate(userId, d, true)
        ));
    }
}

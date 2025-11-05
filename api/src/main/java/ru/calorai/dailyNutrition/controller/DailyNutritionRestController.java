package ru.calorai.dailyNutrition.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.calorai.dailyNutririon.port.in.FindDailyNutritionApi;
import ru.calorai.dailyNutrition.dto.DailyNutritionDTO;
import ru.calorai.dailyNutrition.mapper.DailyNutritionDtoMapper;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class DailyNutritionRestController {

    private final FindDailyNutritionApi findDailyNutritionApi;
    private final DailyNutritionDtoMapper mapper;

    @Operation(
            summary = "Получить КБЖУ на дату",
            description = "Возвращает план, факт, остаток и проценты выполнения"
    )
    @GetMapping("/daily")
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

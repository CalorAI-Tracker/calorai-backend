package ru.calorai.foodDiary.controller;

import io.swagger.v3.oas.annotations.Operation;
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
public class DailyMealIntakeController {

    private final FindDailyMealIntakeApi api;
    private final DailyMealIntakeDtoMapper mapper;

    @Operation(summary = "Факт КБЖУ по приёмам пищи на дату")
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

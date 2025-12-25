package ru.calorai.foodDiary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.calorai.dailyNutririon.port.in.FindDailyMealCompositionApi;
import ru.calorai.foodDiary.dto.DailyMealCompositionDTO;
import ru.calorai.foodDiary.dto.request.CreateFoodDiaryEntryRequest;
import ru.calorai.foodDiary.mapper.FoodDiaryDtoMapper;
import ru.calorai.foodDiary.port.in.CreateFoodDiaryEntryApi;

import java.time.LocalDate;

@RestController
@RequestMapping("/food-diary")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(
        name = "Food Diary API",
        description = "API для управления записями дневника питания"
)
public class FoodDiaryController {

    private final CreateFoodDiaryEntryApi createFoodDiaryEntryApi;
    private final FindDailyMealCompositionApi findDailyMealCompositionApi;

    private final FoodDiaryDtoMapper dtoMapper;

    @Operation(summary = "Создать запись о приёме пищи вручную")
    @PostMapping("/{userId}/entries")
    public ResponseEntity<Long> createEntry(
            @PathVariable("userId") Long userId,
            @RequestBody CreateFoodDiaryEntryRequest request
    ) {
        var domainInput = dtoMapper.toDomain(request);
        domainInput.setUserId(userId);
        return ResponseEntity.ok(
                createFoodDiaryEntryApi.createFoodDiaryEntry(domainInput)
        );
    }

    @Operation(summary = "Список продуктов по приёмам пищи за дату",
            description = "Группировка по приёмам: BREAKFAST, LUNCH, DINNER, SNACK. " +
                    "Каждый продукт с названием, весом и КБЖУ")
    @GetMapping("/{userId}/composition")
    public ResponseEntity<DailyMealCompositionDTO> getMealComposition(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "Дата (по умолчанию сегодня)")
            LocalDate date
    ) {
        var targetDate = date != null ? date : LocalDate.now();
        var domain = findDailyMealCompositionApi.findByUserAndDate(userId, targetDate);
        var dto = dtoMapper.toDto(domain);
        return ResponseEntity.ok(dto);
    }
}


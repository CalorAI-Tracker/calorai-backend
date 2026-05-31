package ru.calorai.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.calorai.dailyNutririon.port.in.FindDailyMealCompositionApi;
import ru.calorai.food.dto.DailyMealCompositionDTO;
import ru.calorai.food.dto.request.CreateFoodDiaryEntryRequest;
import ru.calorai.food.dto.request.UpdateFoodDiaryEntryRequest;
import ru.calorai.food.mapper.FoodDiaryDtoMapper;
import ru.calorai.food.diary.model.cmd.UpdateFoodDiaryEntryCommand;
import ru.calorai.food.diary.port.in.CreateFoodDiaryEntryApi;
import ru.calorai.food.diary.port.in.DeleteFoodDiaryEntryApi;
import ru.calorai.food.diary.port.in.UpdateFoodDiaryEntryApi;

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
    private final UpdateFoodDiaryEntryApi updateFoodDiaryEntryApi;
    private final DeleteFoodDiaryEntryApi deleteFoodDiaryEntryApi;

    private final FoodDiaryDtoMapper dtoMapper;

    @Operation(summary = "Создать запись о приёме пищи")
    @PostMapping("/entries")
    public ResponseEntity<Long> createEntry(
            @RequestBody CreateFoodDiaryEntryRequest request
    ) {
        var command = dtoMapper.toCommand(request);
        return ResponseEntity.ok(createFoodDiaryEntryApi.createFoodDiaryEntry(command));
    }

//    @Operation(summary = "Множественное создание записей о приёме пищи")
//    @PostMapping("/entries/bulk")
//    public ResponseEntity<Long> createBulkEntry(
//            @RequestBody CreateFoodDiaryEntryRequest request
//    ) {
//        var command = dtoMapper.toCommand(request);
//        return ResponseEntity.ok(createFoodDiaryEntryApi.createFoodDiaryEntry(command));
//    }

    @Operation(summary = "Список продуктов по приёмам пищи за дату",
            description = "Группировка по приёмам: BREAKFAST, LUNCH, DINNER, SNACK. " +
                    "Каждый продукт с названием, весом и КБЖУ")
    @GetMapping("/composition")
    public ResponseEntity<DailyMealCompositionDTO> getMealComposition(
            @RequestParam(value = "date", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @Parameter(description = "Дата (по умолчанию сегодня)")
            LocalDate date
    ) {
        var targetDate = date != null ? date : LocalDate.now();
        var domain = findDailyMealCompositionApi.findByUserAndDate(targetDate);
        var dto = dtoMapper.toDto(domain);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "Обновить граммовку записи о приёме пищи")
    @PutMapping("/entries/{entryId}")
    public ResponseEntity<Void> updateEntry(
            @PathVariable("entryId") Long entryId,
            @RequestBody UpdateFoodDiaryEntryRequest request
    ) {
        var command = UpdateFoodDiaryEntryCommand.builder()
                .entryId(entryId)
                .portionQuantityGrams(request.getPortionQuantityGrams())
                .build();
        updateFoodDiaryEntryApi.updateFoodDiaryEntry(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить запись о приёме пищи")
    @DeleteMapping("/entries/{entryId}")
    public ResponseEntity<Void> deleteEntry(
            @PathVariable("entryId") Long entryId
    ) {
        deleteFoodDiaryEntryApi.deleteFoodDiaryEntry(entryId);
        return ResponseEntity.noContent().build();
    }
}


package ru.calorai.meta.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.calorai.heathProfile.port.in.FindActivityLevelsApi;
import ru.calorai.heathProfile.port.in.FindHealthGoalsApi;
import ru.calorai.meta.dto.ActivityLevelDTO;
import ru.calorai.meta.dto.HealthGoalDTO;
import ru.calorai.meta.mapper.ActivityLevelDtoMapper;
import ru.calorai.meta.mapper.HealthGoalDtoMapper;

import java.util.List;

@RestController
@RequestMapping("/meta")
@RequiredArgsConstructor
@Tag(name = "Meta", description = "Справочные данные")
public class MetaRestController {

    private final FindActivityLevelsApi findActivityLevelsApi;
    private final FindHealthGoalsApi findHealthGoalsApi;

    private final ActivityLevelDtoMapper activityLevelDtoMapper;
    private final HealthGoalDtoMapper healthGoalDtoMapper;

    @Operation(summary = "Получить уровни активности")
    @GetMapping("/activity-levels")
    public ResponseEntity<List<ActivityLevelDTO>> getActivityLevels() {
        return ResponseEntity.ok(
                activityLevelDtoMapper.toDtoList(findActivityLevelsApi.findAll())
        );
    }

    @Operation(summary = "Получить цели по здоровью")
    @GetMapping("/health-goals")
    public ResponseEntity<List<HealthGoalDTO>> getHealthGoals() {
        return ResponseEntity.ok(
                healthGoalDtoMapper.toDtoList(findHealthGoalsApi.findAll())
        );
    }
}

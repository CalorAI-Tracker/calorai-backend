package ru.calorai.dailyNutrition.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "КБЖУ на дату: план, факт и остаток")
public class DailyNutritionDTO {

    @Schema(description = "Дата, на которую возвращены данные", example = "2025-11-05")
    private LocalDate date;

    @Schema(description = "Плановые цели на день")
    private TargetDTO plan;

    @Schema(description = "Фактически съедено за день")
    private TargetDTO actual;

    @Schema(description = "Осталось до выполнения плана (может быть отрицательным)")
    private TargetDTO remaining;

    @Schema(description = "Процент выполнения плана по каждому нутриенту, 0..100+")
    private Map<String, Integer> completionPercent;
}

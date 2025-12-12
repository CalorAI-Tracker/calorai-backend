package ru.calorai.dailyNutrition.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Процент выполнения плана по нутриентам, 0..100+")
public class CompletionPercentDTO {

    @Schema(example = "23")
    private Integer kcal;

    @Schema(example = "1")
    private Integer protein;

    @Schema(example = "1")
    private Integer fat;

    @Schema(example = "67")
    private Integer carbs;
}

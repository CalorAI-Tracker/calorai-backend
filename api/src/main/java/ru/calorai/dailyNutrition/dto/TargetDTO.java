package ru.calorai.dailyNutrition.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TargetDTO {
    @Schema(example = "2300") private Integer kcal;
    @Schema(example = "160.00") private String proteinG;
    @Schema(example = "70.00")  private String fatG;
    @Schema(example = "250.00") private String carbsG;
}
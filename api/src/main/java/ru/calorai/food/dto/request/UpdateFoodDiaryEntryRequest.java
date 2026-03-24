package ru.calorai.food.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на обновление записи о приёме пищи")
public class UpdateFoodDiaryEntryRequest {

    @Schema(description = "Фактическая порция (г)", example = "200.0", required = true)
    private BigDecimal portionQuantityGrams;
}

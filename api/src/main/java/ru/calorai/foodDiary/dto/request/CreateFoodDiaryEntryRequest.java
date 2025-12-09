package ru.calorai.foodDiary.dto.request;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Запрос на создание записи о приёме пищи вручную")
public class CreateFoodDiaryEntryRequest {

    @Schema(description = "Название продукта", example = "Гречка варёная", required = true)
    private String entryName;

    @Schema(description = "Приём пищи", example = "LUNCH", required = true)
    private String meal;

    @Schema(description = "Дата приёма пищи (по умолчанию сегодня)", example = "2025-12-09")
    private LocalDate eatenAt;

    @Schema(description = "Белки (г) на базовый объём (обычно 100г)", example = "4.2", required = true)
    private Double proteinPerBaseG;

    @Schema(description = "Жиры (г) на базовый объём (обычно 100г)", example = "1.1", required = true)
    private Double fatPerBaseG;

    @Schema(description = "Углеводы (г) на базовый объём (обычно 100г)", example = "22.5", required = true)
    private Double carbsPerBaseG;

    @Schema(description = "Количество грамм, на которое указано содержание КБЖУ", example = "100.0", defaultValue = "100.0")
    private Double baseQuantityGrams;

    @Schema(description = "Фактическая порция (г)", example = "150.0", required = true)
    private Double portionQuantityGrams;

    @Schema(description = "Бренд/производитель", example = "Увелка")
    private String brand;

    @Schema(description = "Штрихкод продукта", example = "4607084351234")
    private String barcode;

    @Schema(description = "Заметка пользователя", example = "Без масла")
    private String note;
}

package ru.calorai.food.model.cmd;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFoodDiaryEntryCommand {

    // если продукт выбран из каталога
    private Long foodCatalogId;

    // если ручной ввод — КБЖУ на baseQuantityGrams
    private String entryName;
    private String brand;
    private String barcode;
    private Double proteinPerBaseG;
    private Double fatPerBaseG;
    private Double carbsPerBaseG;
    private Double baseQuantityGrams; // на сколько грамм указано КБЖУ (по умолчанию 100)

    // общие поля
    private String meal;
    private LocalDate eatenAt;
    private BigDecimal portionQuantityGrams; // сколько реально съел
    private String note;
}

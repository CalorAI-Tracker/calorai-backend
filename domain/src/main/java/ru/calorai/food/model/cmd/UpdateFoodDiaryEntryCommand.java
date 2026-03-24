package ru.calorai.food.model.cmd;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateFoodDiaryEntryCommand {
    private Long entryId;
    private BigDecimal portionQuantityGrams;
}

package ru.calorai.food.diary.model.cmd;

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

package ru.calorai.foodDiary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.calorai.foodDiary.dto.DailyMealIntakeDTO;
import ru.calorai.foodDiary.model.DailyMealIntake;
import ru.calorai.foodDiary.model.MealIntake;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DailyMealIntakeDtoMapper {

    @Mapping(target = "date", source = "date")
    @Mapping(target = "meals", expression = "java(toItems(d.getItems()))")
    DailyMealIntakeDTO toDto(DailyMealIntake d);

    default List<DailyMealIntakeDTO.Item> toItems(List<MealIntake> list) {
        if (list == null) return java.util.Collections.emptyList();
        return list.stream().map(m ->
                DailyMealIntakeDTO.Item.builder()
                        .meal(m.getMeal())
                        .kcal(m.getKcal())
                        .proteinG(fmt(m.getProteinG()))
                        .fatG(fmt(m.getFatG()))
                        .carbsG(fmt(m.getCarbsG()))
                        .entriesCnt(m.getEntriesCnt())
                        .build()
        ).toList();
    }

    private String fmt(BigDecimal v) {
        if (v == null) return "0.00";
        return v.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}

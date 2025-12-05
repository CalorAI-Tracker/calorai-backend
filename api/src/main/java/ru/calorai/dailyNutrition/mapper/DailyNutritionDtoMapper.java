package ru.calorai.dailyNutrition.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.calorai.dailyNutririon.model.DailyNutrition;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.dailyNutrition.dto.DailyNutritionDTO;
import ru.calorai.dailyNutrition.dto.TargetDTO;

@Mapper(componentModel = "spring")
public interface DailyNutritionDtoMapper {

    @Mapping(target = "date", source = "date")
    @Mapping(target = "plan", expression = "java(toTarget(dn.getPlan()))")
    @Mapping(target = "actual", expression = "java(toTarget(dn.getActual()))")
    @Mapping(target = "remaining", expression = "java(toTarget(dn.remaining()))")
    @Mapping(target = "completionPercent", expression = "java(dn.completionPercent())")
    DailyNutritionDTO toDto(DailyNutrition dn);

    default TargetDTO toTarget(Macro m) {
        return TargetDTO.builder()
                .kcal(m.getKcal())
                .proteinG(fmt(m.getProteinG()))
                .fatG(fmt(m.getFatG()))
                .carbsG(fmt(m.getCarbsG()))
                .build();
    }

    private Double fmt(java.math.BigDecimal v) {
        if (v == null) return null;
        return v.setScale(2, java.math.RoundingMode.HALF_UP).doubleValue();
    }
}

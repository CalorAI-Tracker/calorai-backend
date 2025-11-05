package ru.calorai.dailyTarget.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.dailyTarget.jpa.entity.DailyIntakeTotalEntity;

@Mapper(componentModel = "spring")
public interface DailyIntakeTotalEntityMapper {
    @Mappings({
            @Mapping(target = "kcal", source = "kcal"),
            @Mapping(target = "proteinG", source = "proteinG"),
            @Mapping(target = "fatG", source = "fatG"),
            @Mapping(target = "carbsG", source = "carbsG")
    })
    Macro toMacro(DailyIntakeTotalEntity e);
}

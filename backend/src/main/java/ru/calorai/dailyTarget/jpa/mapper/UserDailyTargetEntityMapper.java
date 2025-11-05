package ru.calorai.dailyTarget.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.dailyTarget.jpa.entity.UserDailyTargetEntity;

@Mapper(componentModel = "spring")
public interface UserDailyTargetEntityMapper {

    @Mappings({
            @Mapping(target = "kcal", source = "kcalTarget"),
            @Mapping(target = "proteinG", source = "proteinGTarget"),
            @Mapping(target = "fatG", source = "fatGTarget"),
            @Mapping(target = "carbsG", source = "carbsGTarget")
    })
    Macro toMacro(UserDailyTargetEntity e);
}

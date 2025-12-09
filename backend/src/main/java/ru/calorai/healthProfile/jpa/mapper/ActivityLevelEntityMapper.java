package ru.calorai.healthProfile.jpa.mapper;

import org.mapstruct.Mapper;
import ru.calorai.healthProfile.jpa.entity.ActivityLevelEntity;
import ru.calorai.heathProfile.model.ActivityLevel;

@Mapper(componentModel = "spring")
public interface ActivityLevelEntityMapper {

    ActivityLevel toDomain(ActivityLevelEntity entity);
}


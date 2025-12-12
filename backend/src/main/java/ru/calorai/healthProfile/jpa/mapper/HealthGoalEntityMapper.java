package ru.calorai.healthProfile.jpa.mapper;

import org.mapstruct.Mapper;
import ru.calorai.healthProfile.jpa.entity.HealthGoalEntity;
import ru.calorai.heathProfile.model.HealthGoal;

@Mapper(componentModel = "spring")
public interface HealthGoalEntityMapper {

    HealthGoal toDomain(HealthGoalEntity entity);
}


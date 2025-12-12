package ru.calorai.meta.mapper;

import org.mapstruct.Mapper;
import ru.calorai.heathProfile.model.HealthGoal;
import ru.calorai.meta.dto.HealthGoalDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HealthGoalDtoMapper {

    HealthGoalDTO toDto(HealthGoal domain);

    List<HealthGoalDTO> toDtoList(List<HealthGoal> goals);
}


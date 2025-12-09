package ru.calorai.meta.mapper;

import org.mapstruct.Mapper;
import ru.calorai.heathProfile.model.ActivityLevel;
import ru.calorai.meta.dto.ActivityLevelDTO;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ActivityLevelDtoMapper {

    ActivityLevelDTO toDto(ActivityLevel domain);

    List<ActivityLevelDTO> toDtoList(List<ActivityLevel> levels);
}


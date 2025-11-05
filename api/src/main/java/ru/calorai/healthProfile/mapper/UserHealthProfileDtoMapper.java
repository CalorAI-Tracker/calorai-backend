package ru.calorai.healthProfile.mapper;

import org.mapstruct.Mapper;
import ru.calorai.healthProfile.dto.UserHealthProfileDTO;
import ru.calorai.healthProfile.dto.request.CreateUserHealthProfileRequest;
import ru.calorai.profile.model.UserHealthProfile;

@Mapper(componentModel = "spring")
public interface UserHealthProfileDtoMapper {

    UserHealthProfile toDomain(CreateUserHealthProfileRequest request);

    UserHealthProfileDTO toDto(UserHealthProfile profile);
}

package ru.calorai.userProfile.mapper;

import org.mapstruct.Mapper;
import ru.calorai.userProfile.dto.UserProfileDTO;
import ru.calorai.userProfile.dto.request.CreateUserProfileRequest;
import ru.calorai.profile.model.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileDtoMapper {

    UserProfile toDomain(CreateUserProfileRequest request);

    UserProfileDTO toDto(UserProfile profile);
}

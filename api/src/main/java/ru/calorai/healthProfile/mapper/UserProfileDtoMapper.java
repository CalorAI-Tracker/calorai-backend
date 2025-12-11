package ru.calorai.healthProfile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.calorai.healthProfile.dto.UserProfileDTO;
import ru.calorai.healthProfile.dto.request.CreateUserProfileRequest;
import ru.calorai.healthProfile.dto.request.UpdateUserProfileRequest;
import ru.calorai.heathProfile.model.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileDtoMapper {

    @Mapping(target = "healthGoal.code", source = "healthGoalCode")
    @Mapping(target = "activityLevel.code", source = "activityCode")
    UserProfile toDomain(CreateUserProfileRequest request);

    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "healthGoal.code", source = "request.healthGoalCode")
    @Mapping(target = "activityLevel.code", source = "request.activityCode")
    UserProfile toDomain(UpdateUserProfileRequest request, Long userId);

    @Mapping(source = "profile.healthGoal.code", target = "healthGoalCode")
    @Mapping(source = "profile.activityLevel.code", target = "activityCode")
    UserProfileDTO toDto(UserProfile profile);
}

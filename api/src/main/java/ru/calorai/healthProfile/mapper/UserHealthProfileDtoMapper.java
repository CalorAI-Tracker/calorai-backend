package ru.calorai.healthProfile.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.calorai.healthProfile.dto.UserHealthProfileDTO;
import ru.calorai.healthProfile.dto.request.CreateUserHealthProfileRequest;
import ru.calorai.healthProfile.dto.request.UpdateUserHealthProfileRequest;
import ru.calorai.heathProfile.model.UserHealthProfile;

@Mapper(componentModel = "spring")
public interface UserHealthProfileDtoMapper {

    @Mapping(target = "healthGoal.id", source = "goalId")
    @Mapping(target = "activityLevel.id", source = "activityId")
    UserHealthProfile toDomain(CreateUserHealthProfileRequest request);

    UserHealthProfile toDomain(UpdateUserHealthProfileRequest request);

    @Mapping(source = "profile.healthGoal.code", target = "healthGoalCode")
    @Mapping(source = "profile.activityLevel.code", target = "activityCode")
    UserHealthProfileDTO toDto(UserHealthProfile profile);
}

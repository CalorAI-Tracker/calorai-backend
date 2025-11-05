package ru.calorai.healthProfile.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.calorai.healthProfile.jpa.entity.UserHealthProfileEntity;
import ru.calorai.profile.model.UserHealthProfile;

@Mapper(componentModel = "spring")
public interface UserHealthProfileEntityMapper {

    @Mapping(target = "activityLevelId", source = "activityId")
    @Mapping(target = "healthGoalId", source = "goalId")
    UserHealthProfileEntity toUserProfileEntity(UserHealthProfile userHealthProfile);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "activityId", source = "activityLevelId")
    @Mapping(target = "goalId", source = "healthGoalId")
    UserHealthProfile toUserProfile(UserHealthProfileEntity userHealthProfileEntity);
}

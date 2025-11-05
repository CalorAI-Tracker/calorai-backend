package ru.calorai.profile.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.calorai.profile.jpa.entity.UserProfileEntity;
import ru.calorai.profile.model.UserProfile;

@Mapper(componentModel = "spring")
public interface UserProfileEntityMapper {

    @Mapping(target = "activityLevelId", source = "activityId")
    @Mapping(target = "healthGoalId", source = "goalId")
    UserProfileEntity toUserProfileEntity(UserProfile userProfile);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "activityId", source = "activityLevelId")
    @Mapping(target = "goalId", source = "healthGoalId")
    UserProfile toUserProfile(UserProfileEntity userProfileEntity);
}

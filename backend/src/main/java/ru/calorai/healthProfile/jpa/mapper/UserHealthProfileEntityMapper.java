package ru.calorai.healthProfile.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import ru.calorai.healthProfile.jpa.entity.ActivityLevelEntity;
import ru.calorai.healthProfile.jpa.entity.HealthGoalEntity;
import ru.calorai.healthProfile.jpa.entity.UserHealthProfileEntity;
import ru.calorai.heathProfile.model.UserHealthProfile;
import ru.calorai.user.jpa.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserHealthProfileEntityMapper {

    @Mappings({
            @Mapping(target = "user", expression = "java(refUser(d.getUserId()))"),
            @Mapping(target = "activityLevel", expression = "java(refActivityLevel(d.getActivityLevel().getId()))"),
            @Mapping(target = "healthGoal", expression = "java(refHealthGoal(d.getHealthGoal().getId()))")
    })
    UserHealthProfileEntity toUserProfileEntity(UserHealthProfile d);

    @Mappings({
            @Mapping(target = "userId",          source = "user.id"),
            @Mapping(target = "activityLevel", source = "activityLevel"),
            @Mapping(target = "healthGoal",    source = "healthGoal")
    })
    UserHealthProfile toUserProfile(UserHealthProfileEntity e);

    default UserEntity refUser(Long id) {
        if (id == null) return null;
        var u = new UserEntity();
        u.setId(id);
        return u;
    }

    default ActivityLevelEntity refActivityLevel(Short id) {
        if (id == null) return null;
        var a = new ActivityLevelEntity();
        a.setId(id);
        return a;
    }

    default HealthGoalEntity refHealthGoal(Short id) {
        if (id == null) return null;
        var h = new HealthGoalEntity();
        h.setId(id);
        return h;
    }
}

package ru.calorai.healthProfile.jpa.mapper;

import org.mapstruct.*;
import ru.calorai.healthProfile.jpa.entity.ActivityLevelEntity;
import ru.calorai.healthProfile.jpa.entity.HealthGoalEntity;
import ru.calorai.healthProfile.jpa.entity.UserProfileEntity;
import ru.calorai.heathProfile.model.UserProfile;
import ru.calorai.user.jpa.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserProfileEntityMapper {

    @Mappings({
            @Mapping(target = "user", expression = "java(refUser(d.getUserId()))"),
            @Mapping(target = "activityLevel", expression = "java(refActivityLevel(d.getActivityLevel().getId()))"),
            @Mapping(target = "healthGoal", expression = "java(refHealthGoal(d.getHealthGoal().getId()))")
    })
    UserProfileEntity toUserProfileEntity(UserProfile d);

    @Mappings({
            @Mapping(target = "email",          source = "user.email"),
            @Mapping(target = "userId",         source = "user.id"),
            @Mapping(target = "name",           source = "user.name"),
            @Mapping(target = "activityLevel",  source = "activityLevel"),
            @Mapping(target = "healthGoal",     source = "healthGoal")
    })
    UserProfile toUserProfile(UserProfileEntity e);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDomain(UserProfile source,
                                @MappingTarget UserProfileEntity target);

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

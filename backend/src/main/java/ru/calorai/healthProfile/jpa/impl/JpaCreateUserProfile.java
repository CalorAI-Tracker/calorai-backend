package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.entity.ActivityLevelEntity;
import ru.calorai.healthProfile.jpa.entity.HealthGoalEntity;
import ru.calorai.healthProfile.jpa.mapper.UserProfileEntityMapper;
import ru.calorai.healthProfile.jpa.repository.ActivityLevelRepository;
import ru.calorai.healthProfile.jpa.repository.HealthGoalRepository;
import ru.calorai.healthProfile.jpa.repository.UserProfileRepository;
import ru.calorai.heathProfile.model.UserProfile;
import ru.calorai.user.jpa.repository.UserRepository;
import ru.calorai.heathProfile.port.out.CreateUserProfileSpi;

@Component
@RequiredArgsConstructor
public class JpaCreateUserProfile implements CreateUserProfileSpi {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    private final HealthGoalRepository healthGoalRepository;
    private final ActivityLevelRepository activityLevelRepository;

    private final UserProfileEntityMapper userProfileEntityMapper;

    @Override
    public UserProfile createUserProfile(UserProfile userProfile) {
        var userProfileEntity = userProfileEntityMapper.toUserProfileEntity(userProfile);

        HealthGoalEntity healthGoalEntity = healthGoalRepository.getByCode(userProfile.getHealthGoal().getCode());
        ActivityLevelEntity activityLevelEntity = activityLevelRepository.getByCode(userProfile.getActivityLevel().getCode());

        userProfileEntity.setHealthGoal(healthGoalEntity);
        userProfileEntity.setActivityLevel(activityLevelEntity);

        // Имя пользователя указывается при создании профиля
        var user = userRepository.findById(userProfile.getUserId()).get();
        user.setName(userProfile.getName());
        userProfileEntity.setUser(user);

        return userProfileEntityMapper.toUserProfile(userProfileRepository.save(userProfileEntity));
    }
}

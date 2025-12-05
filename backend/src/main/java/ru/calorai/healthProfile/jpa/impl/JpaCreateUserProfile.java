package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.mapper.UserProfileEntityMapper;
import ru.calorai.healthProfile.jpa.repository.UserProfileRepository;
import ru.calorai.heathProfile.model.UserProfile;
import ru.calorai.user.jpa.repository.UserRepository;
import ru.calorai.heathProfile.port.out.CreateUserProfileSpi;

@Component
@RequiredArgsConstructor
public class JpaCreateUserProfile implements CreateUserProfileSpi {

    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    private final UserProfileEntityMapper userProfileEntityMapper;

    @Override
    public UserProfile createUserProfile(UserProfile userProfile) {
        var userProfileEntity = userProfileEntityMapper.toUserProfileEntity(userProfile);
        userProfileEntity.setUser(userRepository.findById(userProfile.getUserId()).get());
        return userProfileEntityMapper.toUserProfile(userProfileRepository.save(userProfileEntity));
    }
}

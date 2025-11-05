package ru.calorai.profile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.profile.jpa.mapper.UserProfileEntityMapper;
import ru.calorai.profile.jpa.repository.UserProfileRepository;
import ru.calorai.user.jpa.repository.UserRepository;
import ru.calorai.profile.model.UserProfile;
import ru.calorai.profile.port.out.CreateUserProfileSpi;

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

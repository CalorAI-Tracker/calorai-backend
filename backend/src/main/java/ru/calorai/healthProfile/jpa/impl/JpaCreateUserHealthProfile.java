package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.mapper.UserHealthProfileEntityMapper;
import ru.calorai.healthProfile.jpa.repository.UserHealthProfileRepository;
import ru.calorai.heathProfile.model.UserHealthProfile;
import ru.calorai.user.jpa.repository.UserRepository;
import ru.calorai.heathProfile.port.out.CreateUserHealthProfileSpi;

@Component
@RequiredArgsConstructor
public class JpaCreateUserHealthProfile implements CreateUserHealthProfileSpi {

    private final UserHealthProfileRepository userHealthProfileRepository;

    private final UserRepository userRepository;

    private final UserHealthProfileEntityMapper userHealthProfileEntityMapper;

    @Override
    public UserHealthProfile createUserProfile(UserHealthProfile userHealthProfile) {
        var userProfileEntity = userHealthProfileEntityMapper.toUserProfileEntity(userHealthProfile);
        userProfileEntity.setUser(userRepository.findById(userHealthProfile.getUserId()).get());
        return userHealthProfileEntityMapper.toUserProfile(userHealthProfileRepository.save(userProfileEntity));
    }
}

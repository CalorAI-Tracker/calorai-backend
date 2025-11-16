package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.entity.UserHealthProfileEntity;
import ru.calorai.healthProfile.jpa.mapper.UserHealthProfileEntityMapper;
import ru.calorai.healthProfile.jpa.repository.UserHealthProfileRepository;
import ru.calorai.heathProfile.model.UserHealthProfile;
import ru.calorai.heathProfile.port.out.UpdateUserHealthProfileSpi;
import ru.calorai.user.jpa.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class JpaUpdateUserHealthProfile implements UpdateUserHealthProfileSpi {

    private final UserHealthProfileRepository userHealthProfileRepository;

    private final UserRepository userRepository;

    private final UserHealthProfileEntityMapper userHealthProfileEntityMapper;

    @Override
    public UserHealthProfile updateUserHealthProfile(UserHealthProfile userHealthProfile) {
        UserHealthProfileEntity userProfileEntity = userHealthProfileEntityMapper.toUserProfileEntity(userHealthProfile);
        userProfileEntity.setUser(userRepository.findById(userHealthProfile.getUserId()).get());
        return userHealthProfileEntityMapper.toUserProfile(userHealthProfileRepository.save(userProfileEntity));
    }
}
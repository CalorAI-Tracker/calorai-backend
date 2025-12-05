package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.mapper.UserProfileEntityMapper;
import ru.calorai.healthProfile.jpa.repository.UserProfileRepository;
import ru.calorai.heathProfile.model.UserProfile;
import ru.calorai.heathProfile.port.out.FindUserProfileSpi;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaFindUserProfile implements FindUserProfileSpi {

    private final UserProfileRepository userProfileRepository;

    private final UserProfileEntityMapper userProfileEntityMapper;

    @Override
    public Optional<UserProfile> findUserProfileByUserId(Long id) {
        return userProfileRepository.findByUserId(id).map(userProfileEntityMapper::toUserProfile);
    }
}

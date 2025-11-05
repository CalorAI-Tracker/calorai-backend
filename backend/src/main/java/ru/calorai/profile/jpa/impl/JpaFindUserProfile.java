package ru.calorai.profile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.profile.jpa.mapper.UserProfileEntityMapper;
import ru.calorai.profile.jpa.repository.UserProfileRepository;
import ru.calorai.profile.model.UserProfile;
import ru.calorai.profile.port.out.FindUserProfileSpi;

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

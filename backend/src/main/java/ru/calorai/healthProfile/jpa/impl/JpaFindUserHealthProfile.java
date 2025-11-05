package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.mapper.UserHealthProfileEntityMapper;
import ru.calorai.healthProfile.jpa.repository.UserHealthProfileRepository;
import ru.calorai.heathProfile.model.UserHealthProfile;
import ru.calorai.heathProfile.port.out.FindUserHealthProfileSpi;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaFindUserHealthProfile implements FindUserHealthProfileSpi {

    private final UserHealthProfileRepository userHealthProfileRepository;

    private final UserHealthProfileEntityMapper userHealthProfileEntityMapper;

    @Override
    public Optional<UserHealthProfile> findUserProfileByUserId(Long id) {
        return userHealthProfileRepository.findByUserId(id).map(userHealthProfileEntityMapper::toUserProfile);
    }
}

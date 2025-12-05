package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.entity.UserProfileEntity;
import ru.calorai.healthProfile.jpa.mapper.UserProfileEntityMapper;
import ru.calorai.healthProfile.jpa.repository.UserProfileRepository;
import ru.calorai.heathProfile.model.UserProfile;
import ru.calorai.heathProfile.port.out.UpdateUserProfileSpi;

@Component
@RequiredArgsConstructor
public class JpaUpdateUserProfile implements UpdateUserProfileSpi {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileEntityMapper userProfileEntityMapper;

    @Override
    public UserProfile updateUserHealthProfile(UserProfile userProfile) {
        UserProfileEntity existing =
                userProfileRepository.findByUserId(userProfile.getUserId()).get();

        existing.getUser().setEmail(userProfile.getEmail());
        existing.getUser().setName(userProfile.getName());

        userProfileEntityMapper.updateEntityFromDomain(userProfile, existing);

        UserProfileEntity saved = userProfileRepository.save(existing);
        return userProfileEntityMapper.toUserProfile(saved);
    }
}
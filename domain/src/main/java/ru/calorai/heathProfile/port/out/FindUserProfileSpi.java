package ru.calorai.heathProfile.port.out;

import ru.calorai.heathProfile.model.UserProfile;

import java.util.Optional;

public interface FindUserProfileSpi {
    Optional<UserProfile> findUserProfileByUserId(Long id);
}

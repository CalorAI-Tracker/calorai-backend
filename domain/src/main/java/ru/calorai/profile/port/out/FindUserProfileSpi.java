package ru.calorai.profile.port.out;

import ru.calorai.profile.model.UserProfile;

import java.util.Optional;

public interface FindUserProfileSpi {
    Optional<UserProfile> findUserProfileByUserId(Long id);
}

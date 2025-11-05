package ru.calorai.profile.port.out;

import ru.calorai.profile.model.UserHealthProfile;

import java.util.Optional;

public interface FindUserHealthProfileSpi {
    Optional<UserHealthProfile> findUserProfileByUserId(Long id);
}

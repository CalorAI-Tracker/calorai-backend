package ru.calorai.heathProfile.port.out;

import ru.calorai.heathProfile.model.UserHealthProfile;

import java.util.Optional;

public interface FindUserHealthProfileSpi {
    Optional<UserHealthProfile> findUserProfileByUserId(Long id);
}

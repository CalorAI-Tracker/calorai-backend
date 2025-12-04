package ru.calorai.heathProfile.port.out;

import ru.calorai.heathProfile.model.UserProfile;

@FunctionalInterface
public interface CreateUserProfileSpi {
    UserProfile createUserProfile(UserProfile userProfile);
}

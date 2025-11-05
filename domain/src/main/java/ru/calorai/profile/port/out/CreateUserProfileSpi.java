package ru.calorai.profile.port.out;

import ru.calorai.profile.model.UserProfile;

@FunctionalInterface
public interface CreateUserProfileSpi {
    UserProfile createUserProfile(UserProfile userProfile);
}

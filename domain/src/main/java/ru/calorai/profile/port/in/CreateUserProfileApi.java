package ru.calorai.profile.port.in;

import ru.calorai.profile.model.UserProfile;

@FunctionalInterface
public interface CreateUserProfileApi {
    Long createUserProfile(UserProfile userProfile);
}

package ru.calorai.heathProfile.port.in;

import ru.calorai.heathProfile.model.UserProfile;

@FunctionalInterface
public interface CreateUserProfileApi {
    Long createUserHealthProfile(UserProfile userProfile);
}

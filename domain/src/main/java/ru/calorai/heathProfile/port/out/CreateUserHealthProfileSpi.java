package ru.calorai.heathProfile.port.out;

import ru.calorai.heathProfile.model.UserHealthProfile;

@FunctionalInterface
public interface CreateUserHealthProfileSpi {
    UserHealthProfile createUserProfile(UserHealthProfile userHealthProfile);
}

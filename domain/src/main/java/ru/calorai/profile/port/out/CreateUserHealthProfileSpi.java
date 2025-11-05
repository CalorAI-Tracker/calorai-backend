package ru.calorai.profile.port.out;

import ru.calorai.profile.model.UserHealthProfile;

@FunctionalInterface
public interface CreateUserHealthProfileSpi {
    UserHealthProfile createUserProfile(UserHealthProfile userHealthProfile);
}

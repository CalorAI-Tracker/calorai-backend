package ru.calorai.profile.port.in;

import ru.calorai.profile.model.UserHealthProfile;

@FunctionalInterface
public interface CreateUserHealthProfileApi {
    Long createUserHealthProfile(UserHealthProfile userHealthProfile);
}

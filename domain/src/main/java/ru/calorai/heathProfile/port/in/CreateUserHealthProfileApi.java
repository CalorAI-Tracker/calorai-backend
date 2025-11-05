package ru.calorai.heathProfile.port.in;

import ru.calorai.heathProfile.model.UserHealthProfile;

@FunctionalInterface
public interface CreateUserHealthProfileApi {
    Long createUserHealthProfile(UserHealthProfile userHealthProfile);
}

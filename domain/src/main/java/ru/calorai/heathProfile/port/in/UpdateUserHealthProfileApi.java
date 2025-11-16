package ru.calorai.heathProfile.port.in;

import ru.calorai.heathProfile.model.UserHealthProfile;

public interface UpdateUserHealthProfileApi {
    UserHealthProfile updateUserHealthProfile(Long profileId, UserHealthProfile userHealthProfile);
}

package ru.calorai.heathProfile.port.in;

import ru.calorai.heathProfile.model.UserHealthProfile;

public interface FindUserHealthProfileApi {
    UserHealthProfile findUserProfileByUserId(Long id);
}

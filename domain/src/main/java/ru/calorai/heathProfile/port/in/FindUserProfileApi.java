package ru.calorai.heathProfile.port.in;

import ru.calorai.heathProfile.model.UserProfile;

public interface FindUserProfileApi {
    UserProfile findUserProfileByUserId(Long id);
}

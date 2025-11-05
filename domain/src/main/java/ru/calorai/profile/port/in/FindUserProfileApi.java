package ru.calorai.profile.port.in;

import ru.calorai.profile.model.UserProfile;

public interface FindUserProfileApi {
    UserProfile findUserProfileByUserId(Long id);
}

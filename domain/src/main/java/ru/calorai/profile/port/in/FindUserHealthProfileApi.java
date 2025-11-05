package ru.calorai.profile.port.in;

import ru.calorai.profile.model.UserHealthProfile;

public interface FindUserHealthProfileApi {
    UserHealthProfile findUserProfileByUserId(Long id);
}

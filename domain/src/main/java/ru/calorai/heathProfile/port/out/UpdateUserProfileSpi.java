package ru.calorai.heathProfile.port.out;

import ru.calorai.heathProfile.model.UserProfile;

public interface UpdateUserProfileSpi {
    UserProfile updateUserHealthProfile(UserProfile userProfile);
}

package ru.calorai.heathProfile.port.out;

import ru.calorai.heathProfile.model.UserHealthProfile;

public interface UpdateUserHealthProfileSpi {
    UserHealthProfile updateUserHealthProfile(UserHealthProfile userHealthProfile);
}

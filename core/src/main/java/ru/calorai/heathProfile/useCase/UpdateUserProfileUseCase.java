package ru.calorai.heathProfile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.dailyNutririon.port.in.RecalcTodayTargetsApi;
import ru.calorai.heathProfile.exception.UserProfileNotFoundException;
import ru.calorai.heathProfile.model.UserProfile;
import ru.calorai.heathProfile.port.in.UpdateUserProfileApi;
import ru.calorai.heathProfile.port.out.FindUserProfileSpi;
import ru.calorai.heathProfile.port.out.UpdateUserProfileSpi;

@Service
@RequiredArgsConstructor
public class UpdateUserProfileUseCase implements UpdateUserProfileApi {

    private final FindUserProfileSpi findUserProfileSpi;
    private final UpdateUserProfileSpi updateUserProfileSpi;

    private final RecalcTodayTargetsApi recalcTodayTargetsApi;

    @Override
    @Transactional
    public UserProfile updateUserHealthProfile(UserProfile userProfile) {
        findUserProfileSpi.findUserProfileByUserId(userProfile.getUserId())
                .orElseThrow(() -> new UserProfileNotFoundException(userProfile.getUserId()));

        UserProfile updatedProfile = updateUserProfileSpi.updateUserHealthProfile(userProfile);
        recalcTodayTargetsApi.recalcForToday(userProfile.getUserId());

        return updatedProfile;
    }
}
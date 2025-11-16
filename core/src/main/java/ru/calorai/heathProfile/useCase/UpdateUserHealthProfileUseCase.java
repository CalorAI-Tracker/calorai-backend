package ru.calorai.heathProfile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.dailyNutririon.port.in.RecalcTodayTargetsApi;
import ru.calorai.heathProfile.exception.UserHealthProfileNotFoundException;
import ru.calorai.heathProfile.model.UserHealthProfile;
import ru.calorai.heathProfile.port.in.UpdateUserHealthProfileApi;
import ru.calorai.heathProfile.port.out.FindUserHealthProfileSpi;
import ru.calorai.heathProfile.port.out.UpdateUserHealthProfileSpi;

@Service
@RequiredArgsConstructor
public class UpdateUserHealthProfileUseCase implements UpdateUserHealthProfileApi {

    private final FindUserHealthProfileSpi findUserHealthProfileSpi;
    private final UpdateUserHealthProfileSpi updateUserHealthProfileSpi;
    private final RecalcTodayTargetsApi recalcTodayTargetsApi;

    @Override
    @Transactional
    public UserHealthProfile updateUserHealthProfile(Long userId, UserHealthProfile updatedProfileData) {
        UserHealthProfile existingProfile = findUserHealthProfileSpi.findUserProfileByUserId(userId)
                .orElseThrow(() -> new UserHealthProfileNotFoundException(userId));

        UserHealthProfile updateExistingProfile = mapUpdatedFields(existingProfile, updatedProfileData);

        UserHealthProfile updatedProfile = updateUserHealthProfileSpi.updateUserHealthProfile(updateExistingProfile);

        recalcTodayTargetsApi.recalcForToday(userId);

        return updatedProfile;
    }

    private UserHealthProfile mapUpdatedFields(UserHealthProfile existing, UserHealthProfile newData) {
        return new UserHealthProfile(
                existing.getId(),
                existing.getUserId(),
                newData.getSex() != null ? newData.getSex() : existing.getSex(),
                newData.getHeight() != null ? newData.getHeight() : existing.getHeight(),
                newData.getWeight() != null ? newData.getWeight() : existing.getWeight(),
                newData.getBirthDay() != null ? newData.getBirthDay() : existing.getBirthDay(),
                newData.getHealthGoal() != null ? newData.getHealthGoal() : existing.getHealthGoal(),
                newData.getActivityLevel() != null ? newData.getActivityLevel() : existing.getActivityLevel()
        );
    }
}
package ru.calorai.heathProfile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.util.CheckUserExist;
import ru.calorai.dailyNutririon.port.in.RecalcTodayTargetsApi;
import ru.calorai.heathProfile.exception.UserProfileAlreadyExistException;
import ru.calorai.heathProfile.model.UserProfile;
import ru.calorai.heathProfile.port.in.CreateUserProfileApi;
import ru.calorai.heathProfile.port.out.CreateUserProfileSpi;
import ru.calorai.heathProfile.port.out.FindUserProfileSpi;

@Service
@RequiredArgsConstructor
public class CreateUserProfileUseCase implements CreateUserProfileApi {

    private final CreateUserProfileSpi createUserProfileSpi;
    private final FindUserProfileSpi findUserProfileSpi;

    private final RecalcTodayTargetsApi recalcTodayTargetsApi;

    private final CheckUserExist checkUserExist;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long createUserHealthProfile(UserProfile userProfile) {
        // 1. Проверяем существование пользователя
        checkUserExist.checkUserExist(userProfile.getUserId());

        // 2. Проверяем, не существует ли уже профиль
        if (findUserProfileSpi.findUserProfileByUserId(userProfile.getUserId()).isPresent()) {
            throw new UserProfileAlreadyExistException();
        }

        // 3. Создаем профиль и возвращаем ID
        UserProfile created = createUserProfileSpi.createUserProfile(userProfile);

        // 4. Пересчёт дневных целей на сегодня
        recalcTodayTargetsApi.recalcForToday(created.getUserId());
        return created.getId();
    }
}

package ru.calorai.heathProfile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.util.CheckUserExist;
import ru.calorai.dailyNutririon.port.in.RecalcTodayTargetsApi;
import ru.calorai.heathProfile.exception.UserHealthProfileAlreadyExistException;
import ru.calorai.heathProfile.model.UserHealthProfile;
import ru.calorai.heathProfile.port.in.CreateUserHealthProfileApi;
import ru.calorai.heathProfile.port.out.CreateUserHealthProfileSpi;
import ru.calorai.heathProfile.port.out.FindUserHealthProfileSpi;

@Service
@RequiredArgsConstructor
public class CreateUserHealthProfileUseCase implements CreateUserHealthProfileApi {

    private final CreateUserHealthProfileSpi createUserHealthProfileSpi;
    private final FindUserHealthProfileSpi findUserHealthProfileSpi;

    private final RecalcTodayTargetsApi recalcTodayTargetsApi;

    private final CheckUserExist checkUserExist;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long createUserHealthProfile(UserHealthProfile userHealthProfile) {
        // 1. Проверяем существование пользователя
        checkUserExist.checkUserExist(userHealthProfile.getUserId());

        // 2. Проверяем, не существует ли уже профиль
        findUserHealthProfileSpi.findUserProfileByUserId(userHealthProfile.getUserId())
                .ifPresent(profile -> {
                    throw new UserHealthProfileAlreadyExistException();
                });

        // 3. Создаем профиль и возвращаем ID
        UserHealthProfile created = createUserHealthProfileSpi.createUserProfile(userHealthProfile);

        // 4. Пересчёт дневных целей на сегодня
        recalcTodayTargetsApi.recalcForToday(created.getUserId());
        return created.getId();
    }
}

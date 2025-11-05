package ru.calorai.profile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.util.CheckUserExist;
import ru.calorai.profile.exception.UserHealthProfileAlreadyExistException;
import ru.calorai.profile.model.UserHealthProfile;
import ru.calorai.profile.port.in.CreateUserHealthProfileApi;
import ru.calorai.profile.port.out.CreateUserHealthProfileSpi;
import ru.calorai.profile.port.out.FindUserHealthProfileSpi;

@Service
@RequiredArgsConstructor
public class CreateUserHealthProfileUseCase implements CreateUserHealthProfileApi {

    private final CreateUserHealthProfileSpi createUserHealthProfileSpi;
    private final FindUserHealthProfileSpi findUserHealthProfileSpi;

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
        UserHealthProfile createdProfile = createUserHealthProfileSpi.createUserProfile(userHealthProfile);
        return createdProfile.getId();
    }
}

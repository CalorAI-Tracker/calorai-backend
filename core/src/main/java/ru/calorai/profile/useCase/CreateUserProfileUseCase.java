package ru.calorai.profile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.util.CheckUserExist;
import ru.calorai.profile.exception.UserProfileAlreadyExistException;
import ru.calorai.profile.model.UserProfile;
import ru.calorai.profile.port.in.CreateUserProfileApi;
import ru.calorai.profile.port.out.CreateUserProfileSpi;
import ru.calorai.profile.port.out.FindUserProfileSpi;

@Service
@RequiredArgsConstructor
public class CreateUserProfileUseCase implements CreateUserProfileApi {

    private final CreateUserProfileSpi createUserProfileSpi;
    private final FindUserProfileSpi findUserProfileSpi;

    private final CheckUserExist checkUserExist;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Long createUserProfile(UserProfile userProfile) {
        // 1. Проверяем существование пользователя
        checkUserExist.checkUserExist(userProfile.getUserId());

        // 2. Проверяем, не существует ли уже профиль
        findUserProfileSpi.findUserProfileByUserId(userProfile.getUserId())
                .ifPresent(profile -> {
                    throw new UserProfileAlreadyExistException();
                });

        // 3. Создаем профиль и возвращаем ID
        UserProfile createdProfile = createUserProfileSpi.createUserProfile(userProfile);
        return createdProfile.getId();
    }
}

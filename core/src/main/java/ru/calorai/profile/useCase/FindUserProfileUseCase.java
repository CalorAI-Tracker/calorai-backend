package ru.calorai.profile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.calorai.common.util.CheckUserExist;
import ru.calorai.profile.exception.UserProfileNotFoundException;
import ru.calorai.profile.model.UserProfile;
import ru.calorai.profile.port.in.FindUserProfileApi;
import ru.calorai.profile.port.out.FindUserProfileSpi;

@Service
@RequiredArgsConstructor
public class FindUserProfileUseCase implements FindUserProfileApi {

    private final FindUserProfileSpi findUserProfileSpi;
    private final CheckUserExist checkUserExist;

    @Override
    public UserProfile findUserProfileByUserId(Long userId) {
        checkUserExist.checkUserExist(userId);
        return findUserProfileSpi.findUserProfileByUserId(userId).orElseThrow(
                () -> new UserProfileNotFoundException(userId)
        );
    }
}

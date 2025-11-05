package ru.calorai.profile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.calorai.common.util.CheckUserExist;
import ru.calorai.profile.exception.UserHealthProfileNotFoundException;
import ru.calorai.profile.model.UserHealthProfile;
import ru.calorai.profile.port.in.FindUserHealthProfileApi;
import ru.calorai.profile.port.out.FindUserHealthProfileSpi;

@Service
@RequiredArgsConstructor
public class FindUserHealthHealthProfileUseCase implements FindUserHealthProfileApi {

    private final FindUserHealthProfileSpi findUserHealthProfileSpi;
    private final CheckUserExist checkUserExist;

    @Override
    public UserHealthProfile findUserProfileByUserId(Long userId) {
        checkUserExist.checkUserExist(userId);
        return findUserHealthProfileSpi.findUserProfileByUserId(userId).orElseThrow(
                () -> new UserHealthProfileNotFoundException(userId)
        );
    }
}

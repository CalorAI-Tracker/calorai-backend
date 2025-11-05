package ru.calorai.heathProfile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.calorai.common.util.CheckUserExist;
import ru.calorai.heathProfile.exception.UserHealthProfileNotFoundException;
import ru.calorai.heathProfile.model.UserHealthProfile;
import ru.calorai.heathProfile.port.in.FindUserHealthProfileApi;
import ru.calorai.heathProfile.port.out.FindUserHealthProfileSpi;

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

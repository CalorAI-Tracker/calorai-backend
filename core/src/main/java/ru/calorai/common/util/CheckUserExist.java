package ru.calorai.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.users.exception.UserNotFoundException;
import ru.calorai.users.port.out.FindUserDataSpi;

@Component
@RequiredArgsConstructor
public class CheckUserExist {

    private final FindUserDataSpi findUserDataSpi;

    public void checkUserExist(Long userId) {
        findUserDataSpi.findUserById(userId).orElseThrow(
                () -> new UserNotFoundException(userId)
        );
    }
}

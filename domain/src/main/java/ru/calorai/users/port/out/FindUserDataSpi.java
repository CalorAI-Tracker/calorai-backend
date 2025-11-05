package ru.calorai.users.port.out;

import ru.calorai.users.model.User;

import java.util.Optional;

public interface FindUserDataSpi {
    Optional<User> findUserById(Long id);
}

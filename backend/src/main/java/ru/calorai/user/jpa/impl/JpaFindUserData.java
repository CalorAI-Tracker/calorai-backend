package ru.calorai.user.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.user.jpa.mapper.UserEntityMapper;
import ru.calorai.user.jpa.repository.UserRepository;
import ru.calorai.users.model.User;
import ru.calorai.users.port.out.FindUserDataSpi;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaFindUserData implements FindUserDataSpi {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id).map(userEntityMapper::toDomain);
    }
}

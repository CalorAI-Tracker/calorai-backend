package ru.calorai.profile.jpa.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.calorai.profile.jpa.entity.UserProfileEntity;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {

    @EntityGraph(attributePaths = "user")
    Optional<UserProfileEntity> findByUserId(Long userId);
}

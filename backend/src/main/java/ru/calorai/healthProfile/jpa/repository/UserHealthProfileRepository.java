package ru.calorai.healthProfile.jpa.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.calorai.healthProfile.jpa.entity.UserHealthProfileEntity;

import java.util.Optional;

public interface UserHealthProfileRepository extends JpaRepository<UserHealthProfileEntity, Long> {
    @EntityGraph(attributePaths = "user")
    Optional<UserHealthProfileEntity> findByUserId(Long userId);
}

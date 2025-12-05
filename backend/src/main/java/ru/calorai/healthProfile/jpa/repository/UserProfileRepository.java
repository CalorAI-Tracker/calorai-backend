package ru.calorai.healthProfile.jpa.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.calorai.healthProfile.jpa.entity.UserProfileEntity;

import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
    @EntityGraph(attributePaths = {"user", "healthGoal", "activityLevel"})
    Optional<UserProfileEntity> findByUserId(Long userId);
}

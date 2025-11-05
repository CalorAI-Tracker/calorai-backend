package ru.calorai.healthProfile.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.calorai.healthProfile.jpa.entity.UserHealthHistoryEntity;
import ru.calorai.healthProfile.jpa.entity.id.HealthHistoryId;

import java.util.Optional;

@Repository
public interface UserHealthHistoryRepository extends JpaRepository<UserHealthHistoryEntity, HealthHistoryId> {
    Optional<UserHealthHistoryEntity> findTopByUserIdOrderByMeasuredAtDesc(Long userId);
}

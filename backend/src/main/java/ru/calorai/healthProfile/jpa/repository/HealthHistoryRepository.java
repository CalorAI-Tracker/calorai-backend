package ru.calorai.healthProfile.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.calorai.healthProfile.jpa.entity.HealthHistoryEntity;
import ru.calorai.healthProfile.jpa.entity.id.HHistoryId;

import java.util.Optional;

@Repository
public interface HealthHistoryRepository extends JpaRepository<HealthHistoryEntity, HHistoryId> {
    Optional<HealthHistoryEntity> findTopByUserIdOrderByMeasuredAtDesc(Long userId);
}

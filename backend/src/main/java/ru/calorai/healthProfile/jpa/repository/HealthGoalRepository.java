package ru.calorai.healthProfile.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.calorai.healthProfile.jpa.entity.HealthGoalEntity;

@Repository
public interface HealthGoalRepository extends JpaRepository<HealthGoalEntity, Short> {
    boolean existsByCode(String code);

    HealthGoalEntity getByCode(String code);
}

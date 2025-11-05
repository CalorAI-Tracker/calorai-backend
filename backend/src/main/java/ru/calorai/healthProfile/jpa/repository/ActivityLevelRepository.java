package ru.calorai.healthProfile.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.calorai.healthProfile.jpa.entity.ActivityLevelEntity;

@Repository
public interface ActivityLevelRepository extends JpaRepository<ActivityLevelEntity, Short> {
    boolean existsByCode(String code);
}
package ru.calorai.dailyTarget.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.calorai.dailyTarget.jpa.entity.DailyIntakeTotalEntity;
import ru.calorai.dailyTarget.jpa.entity.id.DailyIntakeTotalId;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyIntakeTotalRepository extends JpaRepository<DailyIntakeTotalEntity, DailyIntakeTotalId> {
    Optional<DailyIntakeTotalEntity> findByUserIdAndDate(Long userId, LocalDate date);
}

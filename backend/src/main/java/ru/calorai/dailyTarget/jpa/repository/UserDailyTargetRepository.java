package ru.calorai.dailyTarget.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.calorai.dailyTarget.jpa.entity.UserDailyTargetEntity;
import ru.calorai.dailyTarget.jpa.entity.UserDailyTargetId;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface UserDailyTargetRepository extends JpaRepository<UserDailyTargetEntity, UserDailyTargetId> {
    Optional<UserDailyTargetEntity> findByUserIdAndDate(Long userId, LocalDate date);
}

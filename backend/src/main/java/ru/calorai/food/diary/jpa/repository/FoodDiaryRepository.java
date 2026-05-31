package ru.calorai.food.diary.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.calorai.food.diary.jpa.entity.FoodDiaryEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FoodDiaryRepository extends JpaRepository<FoodDiaryEntity, Long> {

    List<FoodDiaryEntity> findByUserIdAndEatenAt(Long userId, LocalDate eatenAt);

    Optional<FoodDiaryEntity> findByIdAndUserId(Long id, Long userId);
}

package ru.calorai.foodDiary.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.calorai.foodDiary.jpa.entity.FoodDiaryEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface FoodDiaryRepository extends JpaRepository<FoodDiaryEntity, UUID> {

    interface MealAgg {
        String getMeal();
        Integer getKcal();
        BigDecimal getProteinG();
        BigDecimal getFatG();
        BigDecimal getCarbsG();
        Integer getEntriesCnt();
    }

    @Query("""
        SELECT f.meal as meal,
               COALESCE(SUM(f.kcal), 0) as kcal,
               COALESCE(SUM(f.proteinG), 0) as proteinG,
               COALESCE(SUM(f.fatG), 0) as fatG,
               COALESCE(SUM(f.carbsG), 0) as carbsG,
               COUNT(f) as entriesCnt
        FROM FoodDiaryEntity f
        WHERE f.userId = :userId AND f.eatenAt = :date
        GROUP BY f.meal
    """)
    List<MealAgg> aggregateByMeal(@Param("userId") Long userId, @Param("date") LocalDate date);
}

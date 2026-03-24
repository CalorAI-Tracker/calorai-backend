package ru.calorai.food.diary.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import ru.calorai.food.model.DailyMealIntake;
import ru.calorai.food.model.MealIntake;
import ru.calorai.food.port.out.diary.FindDailyMealIntakeSpi;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class JdbcFindDailyMealIntake implements FindDailyMealIntakeSpi {

    private final JdbcClient jdbcClient;

    @Override
    public DailyMealIntake findByUserAndDate(Long userId, LocalDate date) {
        var items = jdbcClient.sql("""
                SELECT
                    fd.meal,
                    COUNT(fd.id)                                                      AS entries_cnt,
                    COALESCE(SUM(ROUND((fc.kcal_per_100g    * fd.quantity_grams / 100)))::int, 0) AS kcal,
                    COALESCE(SUM(ROUND((fc.protein_per_100g * fd.quantity_grams / 100), 2)), 0)   AS protein_g,
                    COALESCE(SUM(ROUND((fc.fat_per_100g     * fd.quantity_grams / 100), 2)), 0)   AS fat_g,
                    COALESCE(SUM(ROUND((fc.carbs_per_100g   * fd.quantity_grams / 100), 2)), 0)   AS carbs_g
                FROM food_diary fd
                JOIN food_catalog fc ON fc.id = fd.food_catalog_id
                WHERE fd.user_id  = :userId
                  AND fd.eaten_at = :date
                GROUP BY fd.meal
                """)
                .param("userId", userId)
                .param("date", date)
                .query((rs, rowNum) -> MealIntake.builder()
                        .meal(rs.getString("meal"))
                        .entriesCnt(rs.getInt("entries_cnt"))
                        .kcal(rs.getInt("kcal"))
                        .proteinG(rs.getBigDecimal("protein_g"))
                        .fatG(rs.getBigDecimal("fat_g"))
                        .carbsG(rs.getBigDecimal("carbs_g"))
                        .build())
                .list();

        return DailyMealIntake.builder()
                .userId(userId)
                .date(date)
                .items(items)
                .build();
    }
}

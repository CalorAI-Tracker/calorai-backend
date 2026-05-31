package ru.calorai.food.diary.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import ru.calorai.food.diary.model.FoodDiaryEntryWithNutrition;
import ru.calorai.food.diary.port.out.FindFoodDiaryWithNutritionSpi;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JdbcFindFoodDiaryWithNutrition implements FindFoodDiaryWithNutritionSpi {

    private final JdbcClient jdbcClient;

    @Override
    public List<FoodDiaryEntryWithNutrition> findWithNutritionByUserIdAndEatenAt(
            Long userId, LocalDate date) {

        return jdbcClient.sql("""
                SELECT
                    fd.id,
                    fd.user_id,
                    fd.food_catalog_id,
                    fc.name            AS entry_name,
                    fc.brand,
                    fd.eaten_at,
                    fd.meal,
                    fd.quantity_grams,
                    fd.note,
                    ROUND((fc.kcal_per_100g    * fd.quantity_grams / 100))::int AS kcal,
                    ROUND((fc.protein_per_100g * fd.quantity_grams / 100), 2)   AS protein_g,
                    ROUND((fc.fat_per_100g     * fd.quantity_grams / 100), 2)   AS fat_g,
                    ROUND((fc.carbs_per_100g   * fd.quantity_grams / 100), 2)   AS carbs_g
                FROM food_diary fd
                JOIN food_catalog fc ON fc.id = fd.food_catalog_id
                WHERE fd.user_id  = :userId
                  AND fd.eaten_at = :date
                """)
                .param("userId", userId)
                .param("date", date)
                .query((rs, rowNum) -> FoodDiaryEntryWithNutrition.builder()
                        .id(rs.getLong("id"))
                        .userId(rs.getLong("user_id"))
                        .foodCatalogId(rs.getLong("food_catalog_id"))
                        .entryName(rs.getString("entry_name"))
                        .brand(rs.getString("brand"))
                        .eatenAt(rs.getObject("eaten_at", LocalDate.class))
                        .meal(rs.getString("meal"))
                        .quantityGrams(rs.getBigDecimal("quantity_grams"))
                        .note(rs.getString("note"))
                        .kcal(rs.getObject("kcal", Integer.class))
                        .proteinG(rs.getBigDecimal("protein_g"))
                        .fatG(rs.getBigDecimal("fat_g"))
                        .carbsG(rs.getBigDecimal("carbs_g"))
                        .build())
                .list();
    }
}

package ru.calorai.food.diary.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import ru.calorai.dailyNutririon.model.DailyMealComposition;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.dailyNutririon.port.out.FindMealCompositionSpi;
import ru.calorai.food.EMeal;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JdbcFindMealComposition implements FindMealCompositionSpi {

    private final JdbcClient jdbcClient;

    @Override
    public DailyMealComposition findMealCompositionByUserAndDate(Long userId, LocalDate date) {
        var items = jdbcClient.sql("""
                SELECT
                    fd.id                                                                     AS id,
                    fd.meal,
                    fc.name                                                                   AS entry_name,
                    fd.quantity_grams,
                    ROUND((fc.kcal_per_100g    * fd.quantity_grams / 100))::int               AS kcal,
                    ROUND((fc.protein_per_100g * fd.quantity_grams / 100), 2)                 AS protein_g,
                    ROUND((fc.fat_per_100g     * fd.quantity_grams / 100), 2)                 AS fat_g,
                    ROUND((fc.carbs_per_100g   * fd.quantity_grams / 100), 2)                 AS carbs_g
                FROM food_diary fd
                JOIN food_catalog fc ON fc.id = fd.food_catalog_id
                WHERE fd.user_id  = :userId
                  AND fd.eaten_at = :date
                ORDER BY fd.meal, fd.created_at
                """)
                .param("userId", userId)
                .param("date", date)
                .query((rs, rowNum) -> {
                    var macros = Macro.builder()
                            .kcal(rs.getInt("kcal"))
                            .proteinG(rs.getBigDecimal("protein_g"))
                            .fatG(rs.getBigDecimal("fat_g"))
                            .carbsG(rs.getBigDecimal("carbs_g"))
                            .build();

                    return new RawRow(
                            rs.getLong("id"),
                            rs.getString("meal"),
                            rs.getString("entry_name"),
                            rs.getBigDecimal("quantity_grams"),
                            macros
                    );
                })
                .list();

        Map<EMeal, List<DailyMealComposition.MealItem>> grouped = items.stream()
                .collect(Collectors.groupingBy(
                        row -> EMeal.valueOf(row.meal()),
                        Collectors.mapping(
                                row -> new DailyMealComposition.MealItem(
                                        row.id(),
                                        row.entryName(),
                                        row.quantityGrams(),
                                        row.macros()
                                ),
                                Collectors.toList()
                        )
                ));

        return DailyMealComposition.builder()
                .date(date)
                .meals(grouped)
                .build();
    }

    private record RawRow(
            Long id,
            String meal,
            String entryName,
            java.math.BigDecimal quantityGrams,
            Macro macros
    ) {}
}
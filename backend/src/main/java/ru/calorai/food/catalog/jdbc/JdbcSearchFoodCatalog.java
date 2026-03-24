package ru.calorai.food.catalog.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;
import ru.calorai.food.model.FoodCatalogEntry;
import ru.calorai.food.port.out.catalog.SearchFoodCatalogSpi;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JdbcSearchFoodCatalog implements SearchFoodCatalogSpi {

    private final JdbcClient jdbcClient;

    @Override
    public List<FoodCatalogEntry> search(String query, int limit, int offset) {
        return jdbcClient.sql("""
                SELECT id, name, brand, barcode, provider, provider_food_id,
                       created_by, kcal_per_100g, protein_per_100g, fat_per_100g,
                       carbs_per_100g, fiber_per_100g, sugar_per_100g, sodium_per_100g
                FROM food_catalog
                WHERE name  ILIKE '%' || :query || '%'
                   OR brand ILIKE '%' || :query || '%'
                ORDER BY name
                LIMIT :limit OFFSET :offset
                """)
                .param("query", query)
                .param("limit", limit)
                .param("offset", offset)
                .query((rs, rowNum) -> FoodCatalogEntry.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .brand(rs.getString("brand"))
                        .barcode(rs.getString("barcode"))
                        .provider(rs.getString("provider"))
                        .providerFoodId(rs.getString("provider_food_id"))
                        .createdBy(rs.getObject("created_by", Long.class))
                        .kcalPer100g(rs.getBigDecimal("kcal_per_100g"))
                        .proteinPer100g(rs.getBigDecimal("protein_per_100g"))
                        .fatPer100g(rs.getBigDecimal("fat_per_100g"))
                        .carbsPer100g(rs.getBigDecimal("carbs_per_100g"))
                        .fiberPer100g(rs.getBigDecimal("fiber_per_100g"))
                        .sugarPer100g(rs.getBigDecimal("sugar_per_100g"))
                        .sodiumPer100g(rs.getBigDecimal("sodium_per_100g"))
                        .build())
                .list();
    }

    @Override
    public long countByQuery(String query) {
        return jdbcClient.sql("""
                SELECT COUNT(*)
                FROM food_catalog
                WHERE name  ILIKE '%' || :query || '%'
                   OR brand ILIKE '%' || :query || '%'
                """)
                .param("query", query)
                .query(Long.class)
                .single();
    }
}

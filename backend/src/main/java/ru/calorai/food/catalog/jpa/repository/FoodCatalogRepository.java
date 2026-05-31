package ru.calorai.food.catalog.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.calorai.food.catalog.jpa.entity.FoodCatalogEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface FoodCatalogRepository extends JpaRepository<FoodCatalogEntity, Long> {

    Optional<FoodCatalogEntity> findByProviderAndProviderFoodId(String provider, String providerFoodId);

    @Query(value = """
            SELECT * FROM food_catalog
            WHERE name ILIKE '%' || :query || '%'
               OR brand ILIKE '%' || :query || '%'
            ORDER BY name
            LIMIT :limit
            """, nativeQuery = true)
    List<FoodCatalogEntity> search(@Param("query") String query, @Param("limit") int limit);
}
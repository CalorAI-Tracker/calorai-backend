package ru.calorai.food.port.out.catalog;

import ru.calorai.food.model.FoodCatalogEntry;

import java.util.List;
import java.util.Optional;

public interface FindFoodCatalogEntrySpi {
    Optional<FoodCatalogEntry> findByProviderAndProviderFoodId(String provider, String providerFoodId);
    List<FoodCatalogEntry> search(String query, int limit);
}

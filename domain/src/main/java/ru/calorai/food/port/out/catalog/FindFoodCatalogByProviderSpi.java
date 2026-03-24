package ru.calorai.food.port.out.catalog;

import ru.calorai.food.model.FoodCatalogEntry;

import java.util.Optional;

public interface FindFoodCatalogByProviderSpi {
    Optional<FoodCatalogEntry> findByProviderAndProviderFoodId(String provider, String providerFoodId);
}
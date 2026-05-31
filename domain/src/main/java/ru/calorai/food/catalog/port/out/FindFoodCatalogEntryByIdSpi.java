package ru.calorai.food.catalog.port.out;

import ru.calorai.food.catalog.model.FoodCatalogEntry;

import java.util.Optional;

@FunctionalInterface
public interface FindFoodCatalogEntryByIdSpi {
    Optional<FoodCatalogEntry> findById(Long id);
}

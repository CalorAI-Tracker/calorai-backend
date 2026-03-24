package ru.calorai.food.port.out.catalog;

import ru.calorai.food.model.FoodCatalogEntry;

import java.util.Optional;

@FunctionalInterface
public interface FindFoodCatalogEntryByIdSpi {
    Optional<FoodCatalogEntry> findById(Long id);
}

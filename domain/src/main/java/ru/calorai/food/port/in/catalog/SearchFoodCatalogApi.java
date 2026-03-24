package ru.calorai.food.port.in.catalog;

import ru.calorai.food.model.FoodCatalogEntry;

import java.util.List;

@FunctionalInterface
public interface SearchFoodCatalogApi {
    List<FoodCatalogEntry> search(String query, int limit);
}

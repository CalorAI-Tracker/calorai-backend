package ru.calorai.food.catalog.port.out;

import ru.calorai.food.catalog.model.FoodCatalogEntry;

import java.util.List;

public interface FindFoodCatalogEntrySpi {
    List<FoodCatalogEntry> search(String query, int limit);
}

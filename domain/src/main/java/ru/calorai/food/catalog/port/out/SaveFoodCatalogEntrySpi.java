package ru.calorai.food.catalog.port.out;


import ru.calorai.food.catalog.model.FoodCatalogEntry;

public interface SaveFoodCatalogEntrySpi {
    FoodCatalogEntry save(FoodCatalogEntry entry);
}
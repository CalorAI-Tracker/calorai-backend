package ru.calorai.food.port.out.catalog;


import ru.calorai.food.model.FoodCatalogEntry;

public interface SaveFoodCatalogEntrySpi {
    FoodCatalogEntry save(FoodCatalogEntry entry);
}
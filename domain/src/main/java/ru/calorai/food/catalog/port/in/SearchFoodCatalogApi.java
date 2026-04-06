package ru.calorai.food.catalog.port.in;

import ru.calorai.common.model.PageResult;
import ru.calorai.food.catalog.model.FoodCatalogEntry;

@FunctionalInterface
public interface SearchFoodCatalogApi {
    PageResult<FoodCatalogEntry> search(String query, int page, int size);
}

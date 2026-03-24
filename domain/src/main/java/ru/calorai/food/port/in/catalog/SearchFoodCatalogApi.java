package ru.calorai.food.port.in.catalog;

import ru.calorai.common.model.PageResult;
import ru.calorai.food.model.FoodCatalogEntry;

@FunctionalInterface
public interface SearchFoodCatalogApi {
    PageResult<FoodCatalogEntry> search(String query, int page, int size);
}

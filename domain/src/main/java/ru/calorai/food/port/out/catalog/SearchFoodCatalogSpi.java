package ru.calorai.food.port.out.catalog;

import ru.calorai.food.model.FoodCatalogEntry;
import java.util.List;

public interface SearchFoodCatalogSpi {
    List<FoodCatalogEntry> search(String query, int limit, int offset);
    long countByQuery(String query);
}

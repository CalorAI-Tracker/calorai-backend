package ru.calorai.food.seacrh;

import ru.calorai.food.catalog.model.FoodCatalogEntry;

import java.util.List;

public interface SearchExternalFoodProvider {
    ExternalSearchResult search(String query, int page, int pageSize);

    record ExternalSearchResult(
            List<FoodCatalogEntry> items,
            long totalCount
    ) {
        public static ExternalSearchResult empty() {
            return new ExternalSearchResult(List.of(), 0);
        }
    }
}

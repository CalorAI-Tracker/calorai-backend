package ru.calorai.food.useCase.catalog.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import ru.calorai.common.model.PageResult;
import ru.calorai.food.model.FoodCatalogEntry;
import ru.calorai.food.port.in.catalog.SearchFoodCatalogApi;
import ru.calorai.food.port.out.seacrhClient.SearchExternalFoodProvider;
import ru.calorai.food.port.out.catalog.SearchFoodCatalogSpi;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class SearchFoodCatalogUseCase implements SearchFoodCatalogApi {

    private final SearchFoodCatalogSpi searchFoodCatalogSpi;
    private final SearchExternalFoodProvider searchExternalFoodProvider;

    @Override
    public PageResult<FoodCatalogEntry> search(String query, int page, int size) {
        if (query == null || query.isBlank()) {
            return PageResult.empty(page, size);
        }

        var trimmed = query.trim();

        var localResults = searchFoodCatalogSpi.search(trimmed, size, page * size);
        var localTotal   = searchFoodCatalogSpi.countByQuery(trimmed);

        var externalResult = SearchExternalFoodProvider.ExternalSearchResult.empty();
        try {
            externalResult = searchExternalFoodProvider.search(trimmed, page, size);
        } catch (Exception ignored) {}

        var merged = new ArrayList<FoodCatalogEntry>();
        merged.addAll(localResults);
        merged.addAll(externalResult.items());

        var total = localTotal + externalResult.totalCount();

        return PageResult.of(merged, total, page, size);
    }
}

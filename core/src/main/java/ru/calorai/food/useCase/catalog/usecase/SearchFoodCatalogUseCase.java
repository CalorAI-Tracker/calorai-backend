package ru.calorai.food.useCase.catalog.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import ru.calorai.common.model.PageResult;
import ru.calorai.food.model.FoodCatalogEntry;
import ru.calorai.food.port.in.catalog.SearchFoodCatalogApi;
import ru.calorai.food.port.out.catalog.SearchFoodCatalogSpi;


@Service
@RequiredArgsConstructor
public class SearchFoodCatalogUseCase implements SearchFoodCatalogApi {

    private final SearchFoodCatalogSpi searchFoodCatalogSpi;

    @Override
    @Transactional(readOnly = true)
    public PageResult<FoodCatalogEntry> search(String query, int page, int size) {
        if (query == null || query.isBlank()) {
            return PageResult.empty(page, size);
        }

        var trimmed = query.trim();
        var results = searchFoodCatalogSpi.search(trimmed, size, page * size);
        var total = searchFoodCatalogSpi.countByQuery(trimmed);

        return PageResult.of(results, total, page, size);
    }
}

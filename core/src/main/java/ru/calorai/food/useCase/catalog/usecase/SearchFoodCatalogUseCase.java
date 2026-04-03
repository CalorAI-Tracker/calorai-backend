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
        var localResults = searchFoodCatalogSpi.search(query, size, page * size);
        var localTotal   = searchFoodCatalogSpi.countByQuery(query);

        var externalResult = SearchExternalFoodProvider.ExternalSearchResult.empty();
        if (query != null) {
            try {
                externalResult = searchExternalFoodProvider.search(query, page, size);
            } catch (Exception ignored) {
                // игнорируем ошибку от вешнего АПИ
            }
        }

        var merged = new ArrayList<FoodCatalogEntry>();
        merged.addAll(localResults);
        merged.addAll(externalResult.items());

        var total = localTotal + externalResult.totalCount();

        return PageResult.of(merged, total, page, size);
    }
}

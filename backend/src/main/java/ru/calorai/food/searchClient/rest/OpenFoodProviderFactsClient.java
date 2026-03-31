package ru.calorai.food.searchClient.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.calorai.food.port.out.seacrhClient.SearchExternalFoodProvider;
import ru.calorai.food.searchClient.rest.mapper.OpenFoodFactsMapper;
import ru.calorai.food.searchClient.rest.model.OpenFoodFactsResponse;


@Component
@RequiredArgsConstructor
public class OpenFoodProviderFactsClient implements SearchExternalFoodProvider {

    @Value("${external.openfoodfacts.base-url}")
    private String openFoodFactsUrlApi;

    private final OpenFoodFactsMapper openFoodFactsMapper;

    private final RestClient restClient;

    @Override
    public ExternalSearchResult search(String query, int page, int pageSize) {
        try {
            var response = restClient.get()
                    .uri(openFoodFactsUrlApi + "/cgi/search.pl" +
                                    "?search_terms={query}" +
                                    "&search_simple=1" +
                                    "&action=process" +
                                    "&json=1" +
                                    "&page_size={pageSize}" +
                                    "&page={page}" +
                                    "&tagtype_0=countries" +
                                    "&tag_contains_0=contains" +
                                    "&tag_0=Russia",
                            query, pageSize, page)
                    .retrieve()
                    .body(OpenFoodFactsResponse.class);

            if (response == null || response.getProducts() == null) {
                return ExternalSearchResult.empty();
            }

            var items = response.getProducts().stream()
                    .filter(p -> p.getResolvedName() != null)
                    .filter(p -> p.getNutriments() != null)
                    .map(openFoodFactsMapper::toDomain)
                    .toList();

            return new ExternalSearchResult(items, response.getCount() != null ? response.getCount() : 0);

        } catch (Exception e) {
            return ExternalSearchResult.empty();
        }
    }
}

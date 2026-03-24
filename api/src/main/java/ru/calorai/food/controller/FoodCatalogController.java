package ru.calorai.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.calorai.common.mapper.PageResultMapper;
import ru.calorai.common.model.PageResult;
import ru.calorai.food.dto.FoodCatalogEntryDTO;
import ru.calorai.food.dto.request.CatalogSearchPageRequest;
import ru.calorai.food.mapper.FoodCatalogDtoMapper;
import ru.calorai.food.port.in.catalog.SearchFoodCatalogApi;

@RestController
@RequestMapping("/food-catalog")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "Food Catalog API", description = "API для поиска продуктов в каталоге")
public class FoodCatalogController {

    private final SearchFoodCatalogApi searchFoodCatalogApi;

    private final FoodCatalogDtoMapper foodCatalogDtoMapper;

    @Operation(summary = "Поиск продуктов в каталоге")
    @PostMapping("/search")
    public ResponseEntity<PageResult<FoodCatalogEntryDTO>> search(@RequestBody CatalogSearchPageRequest searchPageRequest) {
        var results = searchFoodCatalogApi.search(searchPageRequest.search(), searchPageRequest.pageSettings().page(),
                searchPageRequest.pageSettings().size());
        return ResponseEntity.ok(PageResultMapper.map(results, foodCatalogDtoMapper::toDto));
    }
}

package ru.calorai.food.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.calorai.food.dto.FoodCatalogEntryDTO;
import ru.calorai.food.mapper.FoodCatalogDtoMapper;
import ru.calorai.food.port.in.catalog.SearchFoodCatalogApi;

import java.util.List;

@RestController
@RequestMapping("/food-catalog")
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "Food Catalog API", description = "API для поиска продуктов в каталоге")
public class FoodCatalogController {

    private final SearchFoodCatalogApi searchFoodCatalogApi;
    private final FoodCatalogDtoMapper foodCatalogDtoMapper;

    @Operation(summary = "Поиск продуктов в каталоге")
    @GetMapping("/search")
    public ResponseEntity<List<FoodCatalogEntryDTO>> search(
            @RequestParam("q") String query,
            @RequestParam(value = "limit", defaultValue = "20") int limit
    ) {
        var results = searchFoodCatalogApi.search(query, limit);
        return ResponseEntity.ok(foodCatalogDtoMapper.toDtoList(results));
    }
}

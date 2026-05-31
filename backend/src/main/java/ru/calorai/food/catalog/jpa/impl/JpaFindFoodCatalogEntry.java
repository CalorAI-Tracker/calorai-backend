package ru.calorai.food.catalog.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.food.catalog.jpa.mapper.FoodCatalogEntityMapper;
import ru.calorai.food.catalog.jpa.repository.FoodCatalogRepository;
import ru.calorai.food.catalog.model.FoodCatalogEntry;
import ru.calorai.food.catalog.port.out.FindFoodCatalogEntrySpi;
import ru.calorai.food.catalog.port.out.FindFoodCatalogEntryByIdSpi;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaFindFoodCatalogEntry implements FindFoodCatalogEntrySpi, FindFoodCatalogEntryByIdSpi {

    private final FoodCatalogRepository foodCatalogRepository;
    private final FoodCatalogEntityMapper foodCatalogEntityMapper;

    @Override
    public List<FoodCatalogEntry> search(String query, int limit) {
        return foodCatalogRepository.search(query, limit)
                .stream()
                .map(foodCatalogEntityMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<FoodCatalogEntry> findById(Long id) {
        return foodCatalogRepository.findById(id)
                .map(foodCatalogEntityMapper::toDomain);
    }
}

package ru.calorai.food.catalog.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.food.catalog.jpa.mapper.FoodCatalogEntityMapper;
import ru.calorai.food.catalog.jpa.repository.FoodCatalogRepository;
import ru.calorai.food.model.FoodCatalogEntry;
import ru.calorai.food.port.out.catalog.FindFoodCatalogEntrySpi;
import ru.calorai.food.port.out.catalog.FindFoodCatalogEntryByIdSpi;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaFindFoodCatalogEntry implements FindFoodCatalogEntrySpi, FindFoodCatalogEntryByIdSpi {

    private final FoodCatalogRepository foodCatalogRepository;
    private final FoodCatalogEntityMapper foodCatalogEntityMapper;

    @Override
    public Optional<FoodCatalogEntry> findByProviderAndProviderFoodId(String provider, String providerFoodId) {
        return foodCatalogRepository.findByProviderAndProviderFoodId(provider, providerFoodId)
                .map(foodCatalogEntityMapper::toDomain);
    }

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

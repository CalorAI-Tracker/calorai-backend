package ru.calorai.food.catalog.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.food.catalog.jpa.mapper.FoodCatalogEntityMapper;
import ru.calorai.food.catalog.jpa.repository.FoodCatalogRepository;
import ru.calorai.food.catalog.model.FoodCatalogEntry;
import ru.calorai.food.catalog.port.out.SaveFoodCatalogEntrySpi;

@Component
@RequiredArgsConstructor
public class JpaSaveFoodCatalogEntry implements SaveFoodCatalogEntrySpi {

    private final FoodCatalogRepository foodCatalogRepository;
    private final FoodCatalogEntityMapper foodCatalogEntityMapper;

    @Override
    public FoodCatalogEntry save(FoodCatalogEntry entry) {
        var entity = foodCatalogEntityMapper.toEntity(entry);
        return foodCatalogEntityMapper.toDomain(foodCatalogRepository.save(entity));
    }
}

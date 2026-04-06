package ru.calorai.food.catalog.jpa.mapper;

import org.mapstruct.Mapper;
import ru.calorai.food.catalog.jpa.entity.FoodCatalogEntity;
import ru.calorai.food.catalog.model.FoodCatalogEntry;

@Mapper(componentModel = "spring")
public interface FoodCatalogEntityMapper {

    FoodCatalogEntry toDomain(FoodCatalogEntity entity);

    FoodCatalogEntity toEntity(FoodCatalogEntry domain);
}

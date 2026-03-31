package ru.calorai.food.searchClient.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.calorai.food.model.FoodCatalogEntry;
import ru.calorai.food.searchClient.rest.model.OpenFoodFactsResponse;

@Mapper(componentModel = "spring")
public interface OpenFoodFactsMapper {

    @Mapping(target = "name", expression = "java(p.getResolvedName())")
    @Mapping(target = "brand", source = "brands")
    @Mapping(target = "barcode", source = "code")
    @Mapping(target = "provider", constant = "openfoodfacts")
    @Mapping(target = "providerFoodId", source = "code")
    @Mapping(target = "kcalPer100g", source = "nutriments.kcalPer100g")
    @Mapping(target = "proteinPer100g", source = "nutriments.proteinsPer100g")
    @Mapping(target = "fatPer100g", source = "nutriments.fatPer100g")
    @Mapping(target = "carbsPer100g", source = "nutriments.carbohydratesPer100g")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "fiberPer100g", ignore = true)
    @Mapping(target = "sugarPer100g", ignore = true)
    @Mapping(target = "sodiumPer100g", ignore = true)
    FoodCatalogEntry toDomain(OpenFoodFactsResponse.ProductDTO p);
}

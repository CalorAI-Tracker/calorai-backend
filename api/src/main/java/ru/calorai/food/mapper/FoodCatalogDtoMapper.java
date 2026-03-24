package ru.calorai.food.mapper;

import org.mapstruct.Mapper;
import ru.calorai.food.dto.FoodCatalogEntryDTO;
import ru.calorai.food.model.FoodCatalogEntry;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FoodCatalogDtoMapper {

    FoodCatalogEntryDTO toDto(FoodCatalogEntry entry);

    List<FoodCatalogEntryDTO> toDtoList(List<FoodCatalogEntry> entries);
}

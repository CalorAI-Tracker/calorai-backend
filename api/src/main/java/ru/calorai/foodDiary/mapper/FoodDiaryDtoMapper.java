package ru.calorai.foodDiary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.calorai.foodDiary.dto.request.CreateFoodDiaryEntryRequest;
import ru.calorai.foodDiary.model.FoodDiaryEntry;

@Mapper(componentModel = "spring")
public interface FoodDiaryDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "proteinG", source = ".", qualifiedByName = "calcProtein")
    @Mapping(target = "fatG", source = ".", qualifiedByName = "calcFat")
    @Mapping(target = "carbsG", source = ".", qualifiedByName = "calcCarbs")
    @Mapping(target = "quantityGrams", source = "portionQuantityGrams")
    @Mapping(target = "entryName", source = "entryName")
    @Mapping(target = "meal", source = "meal")
    @Mapping(target = "eatenAt", source = "eatenAt")
    @Mapping(target = "brand", source = "brand")
    @Mapping(target = "barcode", source = "barcode")
    @Mapping(target = "note", source = "note")
    FoodDiaryEntry toDomain(CreateFoodDiaryEntryRequest request);

    @Named("calcProtein")
    default Double calcProtein(CreateFoodDiaryEntryRequest r) {
        return calcMacro(r.getProteinPerBaseG(), r.getBaseQuantityGrams(), r.getPortionQuantityGrams());
    }

    @Named("calcFat")
    default Double calcFat(CreateFoodDiaryEntryRequest r) {
        return calcMacro(r.getFatPerBaseG(), r.getBaseQuantityGrams(), r.getPortionQuantityGrams());
    }

    @Named("calcCarbs")
    default Double calcCarbs(CreateFoodDiaryEntryRequest r) {
        return calcMacro(r.getCarbsPerBaseG(), r.getBaseQuantityGrams(), r.getPortionQuantityGrams());
    }

    default Double calcMacro(Double perBase, Double baseGrams, Double portionGrams) {
        if (perBase == null || baseGrams == null || baseGrams == 0 || portionGrams == null) {
            return null;
        }
        return perBase * (portionGrams / baseGrams);
    }
}

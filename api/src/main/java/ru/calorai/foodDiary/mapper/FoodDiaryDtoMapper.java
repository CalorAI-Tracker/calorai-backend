package ru.calorai.foodDiary.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.calorai.dailyNutririon.model.DailyMealComposition;
import ru.calorai.foodDiary.dto.DailyMealCompositionDTO;
import ru.calorai.foodDiary.dto.request.CreateFoodDiaryEntryRequest;
import ru.calorai.foodDiary.model.EMeal;
import ru.calorai.foodDiary.model.FoodDiaryEntry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Mapping(target = "date", source = "date")
    @Mapping(target = "meals", source = "meals", qualifiedByName = "domainMealsToDto")
    DailyMealCompositionDTO toDto(DailyMealComposition domain);

    @Named("domainMealsToDto")
    default Map<String, List<DailyMealCompositionDTO.MealProductDTO>> domainMealsToDto(
            Map<EMeal, List<DailyMealComposition.MealItem>> domainMeals) {

        return domainMeals.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().name(),
                        entry -> entry.getValue().stream()
                                .map(this::mealItemToProductDto)
                                .collect(Collectors.toList())
                ));
    }

    default DailyMealCompositionDTO.MealProductDTO mealItemToProductDto(DailyMealComposition.MealItem item) {
        return DailyMealCompositionDTO.MealProductDTO.builder()
                .entryName(item.getEntryName())
                .quantityGrams(item.getQuantityGrams())
                .kcal(item.getMacros().getKcal())
                .proteinG(item.getMacros().getProteinG() != null ?
                        item.getMacros().getProteinG().doubleValue() : 0.0)
                .fatG(item.getMacros().getFatG() != null ?
                        item.getMacros().getFatG().doubleValue() : 0.0)
                .carbsG(item.getMacros().getCarbsG() != null ?
                        item.getMacros().getCarbsG().doubleValue() : 0.0)
                .build();
    }

    // Существующие @Named методы
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

    static BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO.setScale(2) : v.setScale(2, RoundingMode.HALF_UP);
    }
}

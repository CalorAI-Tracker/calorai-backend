package ru.calorai.foodDiary.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.calorai.dailyNutririon.model.DailyMealComposition;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.foodDiary.jpa.entity.FoodDiaryEntity;
import ru.calorai.foodDiary.model.EMeal;
import ru.calorai.foodDiary.model.FoodDiaryEntry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FoodDiaryEntityMapper {

    // Существующие методы
    @Mapping(target = "meal", source = "meal", qualifiedByName = "mealToString")
    @Mapping(target = "quantityGrams", source = "quantityGrams", qualifiedByName = "doubleToBd")
    @Mapping(target = "proteinG", source = "proteinG", qualifiedByName = "doubleToBd")
    @Mapping(target = "fatG", source = "fatG", qualifiedByName = "doubleToBd")
    @Mapping(target = "carbsG", source = "carbsG", qualifiedByName = "doubleToBd")
    @Mapping(target = "fiberG", source = "fiberG", qualifiedByName = "doubleToBd")
    @Mapping(target = "sugarG", source = "sugarG", qualifiedByName = "doubleToBd")
    FoodDiaryEntity toEntity(FoodDiaryEntry domain);

    @Mapping(target = "meal", source = "meal", qualifiedByName = "stringToMeal")
    @Mapping(target = "quantityGrams", source = "quantityGrams", qualifiedByName = "bdToDouble")
    @Mapping(target = "proteinG", source = "proteinG", qualifiedByName = "bdToDouble")
    @Mapping(target = "fatG", source = "fatG", qualifiedByName = "bdToDouble")
    @Mapping(target = "carbsG", source = "carbsG", qualifiedByName = "bdToDouble")
    @Mapping(target = "fiberG", source = "fiberG", qualifiedByName = "bdToDouble")
    @Mapping(target = "sugarG", source = "sugarG", qualifiedByName = "bdToDouble")
    FoodDiaryEntry toDomain(FoodDiaryEntity entity);

    // Новые методы для MealComposition
    @Mapping(target = "macros", source = ".", qualifiedByName = "toMealMacros")
    DailyMealComposition.MealItem toMealItem(FoodDiaryEntry entry);

    @Named("toMealMacros")
    default Macro toMealMacros(FoodDiaryEntry entry) {
        return Macro.builder()
                .kcal(entry.getKcal())
                .proteinG(nz(BigDecimal.valueOf(entry.getProteinG())))
                .fatG(nz(BigDecimal.valueOf(entry.getFatG())))
                .carbsG(nz(BigDecimal.valueOf(entry.getCarbsG())))
                .build();
    }

    @Named("entriesToGroupedMealItems")
    default Map<EMeal, List<DailyMealComposition.MealItem>> entriesToGroupedMealItems(List<FoodDiaryEntity> entities) {
        return entities.stream()
                .map(this::toDomain)
                .collect(Collectors.groupingBy(
                        FoodDiaryEntry::getMeal,
                        Collectors.mapping(this::toMealItem, Collectors.toList())
                ));
    }

    // Существующие @Named методы
    @Named("doubleToBd")
    static BigDecimal doubleToBd(Double v) {
        if (v == null) return null;
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.HALF_UP);
    }

    @Named("bdToDouble")
    static Double bdToDouble(BigDecimal v) {
        return v != null ? v.doubleValue() : null;
    }

    @Named("mealToString")
    static String mealToString(EMeal meal) {
        return meal != null ? meal.name() : null;
    }

    @Named("stringToMeal")
    static EMeal stringToMeal(String meal) {
        return meal != null ? EMeal.valueOf(meal) : null;
    }

    static BigDecimal nz(BigDecimal v) {
        return v == null ? BigDecimal.ZERO.setScale(2) : v.setScale(2, RoundingMode.HALF_UP);
    }
}


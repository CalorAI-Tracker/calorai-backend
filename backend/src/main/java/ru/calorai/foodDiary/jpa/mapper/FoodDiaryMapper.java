package ru.calorai.foodDiary.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.calorai.foodDiary.jpa.entity.FoodDiaryEntity;
import ru.calorai.foodDiary.model.EMeal;
import ru.calorai.foodDiary.model.FoodDiaryEntry;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper(componentModel = "spring")
public interface FoodDiaryMapper {

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
}


package ru.calorai.food.diary.jpa.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.calorai.food.diary.jpa.entity.FoodDiaryEntity;
import ru.calorai.food.diary.model.FoodDiaryEntry;


@Mapper(componentModel = "spring")
public interface FoodDiaryEntityMapper {

    @Mapping(target = "meal", source = "meal")
    FoodDiaryEntity toEntity(FoodDiaryEntry domain);

    @Mapping(target = "meal", source = "meal")
    FoodDiaryEntry toDomain(FoodDiaryEntity entity);
}


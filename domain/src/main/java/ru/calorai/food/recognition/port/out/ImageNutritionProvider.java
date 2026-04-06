package ru.calorai.food.recognition.port.out;

import ru.calorai.food.catalog.model.FoodCatalogEntry;

public interface ImageNutritionProvider {
    FoodCatalogEntry getNutrition(Long imageId);
}

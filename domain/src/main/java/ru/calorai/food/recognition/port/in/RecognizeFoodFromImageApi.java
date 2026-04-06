package ru.calorai.food.recognition.port.in;

import ru.calorai.food.catalog.model.FoodCatalogEntry;

public interface RecognizeFoodFromImageApi {
    FoodCatalogEntry recognizeFoodByImage(byte[] imageBytes, String filename);
}

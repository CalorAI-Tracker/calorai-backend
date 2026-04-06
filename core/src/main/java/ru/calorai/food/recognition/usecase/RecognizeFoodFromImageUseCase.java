package ru.calorai.food.recognition.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.calorai.food.catalog.model.FoodCatalogEntry;
import ru.calorai.food.recognition.port.in.RecognizeFoodFromImageApi;
import ru.calorai.food.recognition.port.out.ImageNutritionProvider;
import ru.calorai.food.recognition.port.out.ImageSegmentationProvider;

@Service
@RequiredArgsConstructor
public class RecognizeFoodFromImageUseCase implements RecognizeFoodFromImageApi {

    private final ImageNutritionProvider nutritionProvider;
    private final ImageSegmentationProvider segmentationProvider;

    @Override
    public FoodCatalogEntry recognizeFoodByImage(byte[] imageBytes, String filename) {
        long imageId = segmentationProvider.segment(imageBytes, filename);
        return nutritionProvider.getNutrition(imageId);
    }
}

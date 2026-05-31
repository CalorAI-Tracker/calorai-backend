package ru.calorai.food.recognition.client.rest;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import ru.calorai.food.catalog.model.FoodCatalogEntry;
import ru.calorai.food.recognition.port.out.ImageNutritionProvider;
import ru.calorai.food.recognition.port.out.ImageSegmentationProvider;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class LogMealClient implements ImageSegmentationProvider, ImageNutritionProvider {

    private final RestClient logMealRestClient;

    public LogMealClient(@Qualifier("logMeal") RestClient logMealRestClient) {
        this.logMealRestClient = logMealRestClient;
    }

    @Override
    public Long segment(byte[] imageBytes, String filename) {
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new ByteArrayResource(imageBytes) {
            @Override
            public String getFilename() {
                return filename;
            }
        });

        SegmentationResponse response = logMealRestClient.post()
                .uri("/image/segmentation/complete")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(body)
                .retrieve()
                .body(SegmentationResponse.class);

        return Objects.requireNonNull(response).imageId();
    }

    @Override
    public FoodCatalogEntry getNutrition(Long imageId) {
        NutritionalInfoResponse response = logMealRestClient.post()
                .uri("/nutrition/recipe/nutritionalInfo")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("imageId", imageId))
                .retrieve()
                .body(NutritionalInfoResponse.class);

        Objects.requireNonNull(response);

        BigDecimal servingSize = response.servingSize();
        NutritionalInfoResponse.TotalNutrients n = response.nutritionalInfo().totalNutrients();

        return FoodCatalogEntry.builder()
                .name(String.join(", ", response.foodName()))
                .provider("logmeal")
                .kcalPer100g(per100g(n.kcal().quantity(), servingSize))
                .proteinPer100g(per100g(n.protein().quantity(), servingSize))
                .fatPer100g(per100g(n.fat().quantity(), servingSize))
                .carbsPer100g(per100g(n.carbs().quantity(), servingSize))
                .fiberPer100g(per100g(n.fiber().quantity(), servingSize))
                .sugarPer100g(per100g(n.sugar().quantity(), servingSize))
                .sodiumPer100g(per100g(n.sodium().quantity(), servingSize))
                .build();
    }

    private BigDecimal per100g(BigDecimal quantity, BigDecimal servingSize) {
        return quantity
                .divide(servingSize, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);
    }

    private record SegmentationResponse(
            @JsonProperty("imageId") long imageId
    ) {}

    private record NutritionalInfoResponse(
            @JsonProperty("foodName") List<String> foodName,
            @JsonProperty("nutritional_info") NutritionalInfo nutritionalInfo,
            @JsonProperty("serving_size") BigDecimal servingSize
    ) {
        private record NutritionalInfo(
                @JsonProperty("totalNutrients") TotalNutrients totalNutrients
        ) {}

        private record TotalNutrients(
                @JsonProperty("ENERC_KCAL") Nutrient kcal,
                @JsonProperty("PROCNT") Nutrient protein,
                @JsonProperty("FAT") Nutrient fat,
                @JsonProperty("CHOCDF") Nutrient carbs,
                @JsonProperty("FIBTG") Nutrient fiber,
                @JsonProperty("SUGAR") Nutrient sugar,
                @JsonProperty("NA") Nutrient sodium
        ) {}

        private record Nutrient(
                @JsonProperty("quantity") BigDecimal quantity
        ) {}
    }
}
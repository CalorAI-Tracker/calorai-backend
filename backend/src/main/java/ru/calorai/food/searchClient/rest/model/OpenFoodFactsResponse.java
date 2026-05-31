package ru.calorai.food.searchClient.rest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenFoodFactsResponse {

    @JsonProperty("products")
    private List<ProductDTO> products;

    @JsonProperty("count")
    private Integer count;

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ProductDTO {

        @JsonProperty("code")
        private String code;

        @JsonProperty("product_name_ru")
        private String productNameRu;

        @JsonProperty("product_name")
        private String productName;

        @JsonProperty("brands")
        private String brands;

        @JsonProperty("nutriments")
        private NutrimentsDTO nutriments;

        public String getResolvedName() {
            if (productNameRu != null && !productNameRu.isBlank()) return productNameRu;
            if (productName != null && !productName.isBlank()) return productName;
            return null;
        }
    }

    @Getter
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NutrimentsDTO {

        @JsonProperty("energy-kcal_100g")
        private Double kcalPer100g;

        @JsonProperty("proteins_100g")
        private Double proteinsPer100g;

        @JsonProperty("fat_100g")
        private Double fatPer100g;

        @JsonProperty("carbohydrates_100g")
        private Double carbohydratesPer100g;
    }
}

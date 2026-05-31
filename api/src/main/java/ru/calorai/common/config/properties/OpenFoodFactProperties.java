package ru.calorai.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.openfoodfacts.api")
public record OpenFoodFactProperties(String baseUrl, String token) {}
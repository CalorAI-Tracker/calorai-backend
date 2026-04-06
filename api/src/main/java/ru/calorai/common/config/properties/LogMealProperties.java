package ru.calorai.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external.logmeal.api")
public record LogMealProperties(String baseUrl, String token) {}

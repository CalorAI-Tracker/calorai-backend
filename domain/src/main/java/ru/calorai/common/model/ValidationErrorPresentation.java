package ru.calorai.common.model;

import java.util.Date;
import java.util.Map;

public record ValidationErrorPresentation (
        int statusCode,
        Date timestamp,
        String message,
        Map<String, String> description
) {}
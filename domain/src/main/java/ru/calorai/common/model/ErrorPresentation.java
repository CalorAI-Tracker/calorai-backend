package ru.calorai.common.model;

import java.util.Date;

public record ErrorPresentation (
        int statusCode,
        Date timestamp,
        String message,
        String description
) {}

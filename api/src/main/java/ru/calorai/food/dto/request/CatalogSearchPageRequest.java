package ru.calorai.food.dto.request;

import ru.calorai.common.dto.PageSettings;

public record CatalogSearchPageRequest(PageSettings pageSettings, String search) {
}

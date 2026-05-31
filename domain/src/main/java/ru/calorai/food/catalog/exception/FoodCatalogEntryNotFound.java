package ru.calorai.food.catalog.exception;

public class FoodCatalogEntryNotFound extends RuntimeException {
  public FoodCatalogEntryNotFound(Long id) {
    super("Запись в каталоге не найдена: id=%d".formatted(id));
  }
}

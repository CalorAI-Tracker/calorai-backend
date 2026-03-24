package ru.calorai.food.exception;

public class FoodDiaryEntryNotFoundException extends RuntimeException {

    public FoodDiaryEntryNotFoundException(Long userId, Long entryId) {
        super("Запись дневника питания не найдена: userId=%d, entryId=%d".formatted(userId, entryId));
    }
}

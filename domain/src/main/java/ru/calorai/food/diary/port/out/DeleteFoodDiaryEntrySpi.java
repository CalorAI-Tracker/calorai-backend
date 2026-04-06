package ru.calorai.food.diary.port.out;

@FunctionalInterface
public interface DeleteFoodDiaryEntrySpi {
    void deleteByIdAndUserId(Long entryId, Long userId);
}

package ru.calorai.food.port.out.diary;

@FunctionalInterface
public interface DeleteFoodDiaryEntrySpi {
    void deleteByIdAndUserId(Long entryId, Long userId);
}

package ru.calorai.foodDiary.port.out;

@FunctionalInterface
public interface DeleteFoodDiaryEntrySpi {
    void deleteByIdAndUserId(Long entryId, Long userId);
}

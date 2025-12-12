package ru.calorai.foodDiary.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.foodDiary.model.DailyMealIntake;
import ru.calorai.foodDiary.port.in.FindDailyMealIntakeApi;
import ru.calorai.foodDiary.port.out.FindInFoodDiarySpi;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FindDailyMealIntakeUseCase implements FindDailyMealIntakeApi {

    private final FindInFoodDiarySpi spi;

    @Override
    @Transactional(readOnly = true)
    public DailyMealIntake findByUserAndDate(Long userId, LocalDate date) {
        return spi.findByUserAndDate(userId, date);
    }
}

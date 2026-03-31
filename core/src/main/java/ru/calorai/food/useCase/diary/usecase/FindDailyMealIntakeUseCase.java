package ru.calorai.food.useCase.diary.usecase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.food.model.DailyMealIntake;
import ru.calorai.food.port.in.diary.FindDailyMealIntakeApi;
import ru.calorai.food.port.out.diary.FindDailyMealIntakeSpi;
import ru.calorai.food.port.out.diary.FindInFoodDiarySpi;
import ru.calorai.security.port.in.CurrentUserExtractorApi;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class FindDailyMealIntakeUseCase implements FindDailyMealIntakeApi {

    private final FindDailyMealIntakeSpi findDailyMealIntakeSpi;

    private final CurrentUserExtractorApi currentUserExtractor;

    @Override
    @Transactional(readOnly = true)
    public DailyMealIntake findByUserAndDate(LocalDate date) {
        return findDailyMealIntakeSpi.findByUserAndDate(currentUserExtractor.getUser().getId(), date);
    }
}

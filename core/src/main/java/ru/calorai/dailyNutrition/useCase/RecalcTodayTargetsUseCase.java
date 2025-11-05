package ru.calorai.dailyNutrition.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.dailyNutririon.model.UserDailyTarget;
import ru.calorai.dailyNutririon.port.in.RecalcTodayTargetsApi;
import ru.calorai.dailyNutririon.port.out.UpsertUserDailyTargetsSpi;
import ru.calorai.heathProfile.enums.ESex;
import ru.calorai.heathProfile.port.in.FindUserHealthProfileApi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class RecalcTodayTargetsUseCase implements RecalcTodayTargetsApi {

    private final FindUserHealthProfileApi findUserProfileApi;
    private final UpsertUserDailyTargetsSpi userDailyTargetsSpi;

    @Override
    @Transactional
    public void recalcForToday(Long userId) {
        var profile = findUserProfileApi.findUserProfileByUserId(userId);
        var today = LocalDate.now();

        // расчёт BMR
        int age = profile.getBirthDay() != null
                ? Period.between(profile.getBirthDay(), today).getYears()
                : 30;

        double weight = profile.getWeight().doubleValue();
        double height = profile.getHeight();

        double bmr = (profile.getSex().equals(ESex.M))
                ? 10*weight + 6.25*height - 5*age + 5
                : 10*weight + 6.25*height - 5*age - 161;

        double factor = profile.getActivityLevel().getFactor().doubleValue();
        double tdee = bmr * factor;

        int deltaPct = profile.getHealthGoal().getCalorieDeltaPercent();
        int targetKcal = (int) Math.round(tdee * (1.0 + deltaPct / 100.0));

        double proteinG = Math.round(1.8 * weight);
        double fatG     = Math.round(0.9 * weight);
        double carbsG   = Math.max(0, Math.round((targetKcal - (proteinG*4 + fatG*9)) / 4.0));

        var target = UserDailyTarget.builder()
                .userId(userId)
                .date(today)
                .kcal(targetKcal)
                .proteinG(BigDecimal.valueOf(proteinG))
                .fatG(BigDecimal.valueOf(fatG))
                .carbsG(BigDecimal.valueOf(carbsG))
                .source("mifflin_st_jeor")
                .build();

        userDailyTargetsSpi.upsertUserDailyTarget(target);
    }
}

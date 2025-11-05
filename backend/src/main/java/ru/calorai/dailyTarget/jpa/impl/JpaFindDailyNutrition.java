package ru.calorai.dailyTarget.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.dailyNutririon.model.DailyNutrition;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.dailyNutririon.port.out.FindDailyNutritionSpi;
import ru.calorai.dailyTarget.jpa.mapper.DailyIntakeTotalEntityMapper;
import ru.calorai.dailyTarget.jpa.mapper.UserDailyTargetEntityMapper;
import ru.calorai.dailyTarget.jpa.repository.DailyIntakeTotalRepository;
import ru.calorai.dailyTarget.jpa.repository.UserDailyTargetRepository;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JpaFindDailyNutrition implements FindDailyNutritionSpi {

    private final UserDailyTargetRepository targetsRepo;
    private final DailyIntakeTotalRepository totalsRepo;

    private final UserDailyTargetEntityMapper targetsMapper;
    private final DailyIntakeTotalEntityMapper totalsMapper;

    @Override
    public Optional<DailyNutrition> findByUserAndDate(Long userId, LocalDate date) {
        var planOpt = targetsRepo.findByUserIdAndDate(userId, date);
        if (planOpt.isEmpty()) return Optional.empty();

        var plan = targetsMapper.toMacro(planOpt.get());
        var actual = totalsRepo.findByUserIdAndDate(userId, date)
                .map(totalsMapper::toMacro)
                .orElse(Macro.zero());

        var dn = DailyNutrition.builder()
                .userId(userId)
                .date(date)
                .plan(plan)
                .actual(actual)
                .build();

        return Optional.of(dn);
    }
}

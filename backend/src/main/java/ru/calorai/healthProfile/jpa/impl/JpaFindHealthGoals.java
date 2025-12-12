package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.mapper.HealthGoalEntityMapper;
import ru.calorai.healthProfile.jpa.repository.HealthGoalRepository;
import ru.calorai.heathProfile.model.HealthGoal;
import ru.calorai.heathProfile.port.out.FindHealthGoalsSpi;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaFindHealthGoals implements FindHealthGoalsSpi {

    private final HealthGoalRepository healthGoalRepository;
    private final HealthGoalEntityMapper healthGoalEntityMapper;

    @Override
    public List<HealthGoal> findAll() {
        return healthGoalRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(healthGoalEntityMapper::toDomain)
                .toList();
    }
}


package ru.calorai.heathProfile.port.out;

import ru.calorai.heathProfile.model.HealthGoal;

import java.util.List;

public interface FindHealthGoalsSpi {

    List<HealthGoal> findAll();
}


package ru.calorai.heathProfile.port.in;

import ru.calorai.heathProfile.model.HealthGoal;

import java.util.List;

public interface FindHealthGoalsApi {

    List<HealthGoal> findAll();
}


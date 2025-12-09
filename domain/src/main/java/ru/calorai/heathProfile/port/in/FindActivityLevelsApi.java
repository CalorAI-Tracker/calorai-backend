package ru.calorai.heathProfile.port.in;

import ru.calorai.heathProfile.model.ActivityLevel;

import java.util.List;

public interface FindActivityLevelsApi {

    List<ActivityLevel> findAll();
}


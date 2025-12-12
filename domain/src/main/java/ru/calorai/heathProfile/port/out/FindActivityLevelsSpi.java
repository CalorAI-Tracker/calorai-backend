package ru.calorai.heathProfile.port.out;

import ru.calorai.heathProfile.model.ActivityLevel;

import java.util.List;

public interface FindActivityLevelsSpi {

    List<ActivityLevel> findAll();
}


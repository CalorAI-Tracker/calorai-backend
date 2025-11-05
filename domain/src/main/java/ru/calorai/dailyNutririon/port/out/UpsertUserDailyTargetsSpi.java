package ru.calorai.dailyNutririon.port.out;

import ru.calorai.dailyNutririon.model.UserDailyTarget;

public interface UpsertUserDailyTargetsSpi {
    void upsertUserDailyTarget(UserDailyTarget target);
}

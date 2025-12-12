package ru.calorai.dailyNutririon.port.out;

import ru.calorai.dailyNutririon.model.Macro;

import java.time.LocalDate;

public interface UpsertDailyIntakeTotalsSpi {
    void upsertDailyIntakeTotals(Long userId, LocalDate date, Macro actual, int entriesCount);
}

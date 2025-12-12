package ru.calorai.dailyTarget.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.dailyNutririon.model.Macro;
import ru.calorai.dailyNutririon.port.out.UpsertDailyIntakeTotalsSpi;
import ru.calorai.dailyTarget.jpa.entity.DailyIntakeTotalEntity;
import ru.calorai.dailyTarget.jpa.entity.id.DailyIntakeTotalId;
import ru.calorai.dailyTarget.jpa.mapper.DailyIntakeTotalEntityMapper;
import ru.calorai.dailyTarget.jpa.repository.DailyIntakeTotalRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class JpaUpsertDailyIntakeTotals implements UpsertDailyIntakeTotalsSpi {

    private final DailyIntakeTotalRepository totalsRepo;
    private final DailyIntakeTotalEntityMapper totalsMapper;

    @Override
    public void upsertDailyIntakeTotals(Long userId, LocalDate date, Macro actual, int entriesCount) {
        var id = new DailyIntakeTotalId(userId, date);

        var entity = totalsRepo.findById(id)
                .orElseGet(() -> DailyIntakeTotalEntity.builder()
                        .userId(userId)
                        .date(date)
                        .build()
                );

        entity.setKcal(actual.getKcal());
        entity.setProteinG(actual.getProteinG());
        entity.setFatG(actual.getFatG());
        entity.setCarbsG(actual.getCarbsG());
        entity.setEntriesCnt(entriesCount);

        totalsRepo.save(entity);
    }
}

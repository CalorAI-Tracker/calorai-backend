package ru.calorai.dailyTarget.jpa.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.calorai.dailyNutririon.model.UserDailyTarget;
import ru.calorai.dailyNutririon.port.out.UpsertUserDailyTargetsSpi;
import ru.calorai.dailyTarget.jpa.entity.UserDailyTargetEntity;
import ru.calorai.dailyTarget.jpa.entity.id.UserDailyTargetId;
import ru.calorai.dailyTarget.jpa.repository.UserDailyTargetRepository;

@Component
@RequiredArgsConstructor
public class JpaUpsertUserDailyTarget implements UpsertUserDailyTargetsSpi {

    private final UserDailyTargetRepository userDailyTargetRepository;

    @Override
    public void upsertUserDailyTarget(UserDailyTarget target) {
        var id = new UserDailyTargetId(target.getUserId(), target.getDate());

        var entity = userDailyTargetRepository.findById(id).orElseGet(() -> {
            var e = new UserDailyTargetEntity();
            e.setUserId(target.getUserId());
            e.setDate(target.getDate());
            return e;
        });

        entity.setKcalTarget(target.getKcal());
        entity.setProteinGTarget(target.getProteinG());
        entity.setFatGTarget(target.getFatG());
        entity.setCarbsGTarget(target.getCarbsG());
        entity.setSource(target.getSource());

        userDailyTargetRepository.save(entity);
    }
}

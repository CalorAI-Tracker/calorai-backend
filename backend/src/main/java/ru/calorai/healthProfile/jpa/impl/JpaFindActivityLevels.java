package ru.calorai.healthProfile.jpa.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import ru.calorai.healthProfile.jpa.mapper.ActivityLevelEntityMapper;
import ru.calorai.healthProfile.jpa.repository.ActivityLevelRepository;
import ru.calorai.heathProfile.model.ActivityLevel;
import ru.calorai.heathProfile.port.out.FindActivityLevelsSpi;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JpaFindActivityLevels implements FindActivityLevelsSpi {

    private final ActivityLevelRepository activityLevelRepository;
    private final ActivityLevelEntityMapper activityLevelEntityMapper;

    @Override
    public List<ActivityLevel> findAll() {
        return activityLevelRepository
                .findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream()
                .map(activityLevelEntityMapper::toDomain)
                .toList();
    }
}


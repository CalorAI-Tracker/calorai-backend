package ru.calorai.heathProfile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.heathProfile.model.ActivityLevel;
import ru.calorai.heathProfile.port.in.FindActivityLevelsApi;
import ru.calorai.heathProfile.port.out.FindActivityLevelsSpi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindActivityLevelsUseCase implements FindActivityLevelsApi {

    private final FindActivityLevelsSpi findActivityLevelsSpi;

    @Override
    @Transactional(readOnly = true)
    public List<ActivityLevel> findAll() {
        return findActivityLevelsSpi.findAll();
    }
}


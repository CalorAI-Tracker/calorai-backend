package ru.calorai.heathProfile.useCase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.calorai.heathProfile.model.HealthGoal;
import ru.calorai.heathProfile.port.in.FindHealthGoalsApi;
import ru.calorai.heathProfile.port.out.FindHealthGoalsSpi;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindHealthGoalsUseCase implements FindHealthGoalsApi {

    private final FindHealthGoalsSpi findHealthGoalsSpi;

    @Override
    @Transactional(readOnly = true)
    public List<HealthGoal> findAll() {
        return findHealthGoalsSpi.findAll();
    }
}


package ru.calorai.foodDiary.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import ru.calorai.foodDiary.port.out.DeleteFoodDiaryEntrySpi;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class JdbcDeleteFoodDiaryEntry implements DeleteFoodDiaryEntrySpi {

    private final JdbcClient jdbcClient;

    @Override
    public void deleteByIdAndUserId(Long entryId, Long userId) {
        jdbcClient.sql("DELETE FROM food_diary WHERE id = :id AND user_id = :userId")
                .params(Map.of("id", entryId, "userId", userId))
                .update();
    }
}

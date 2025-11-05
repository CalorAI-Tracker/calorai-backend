package ru.calorai.profile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.calorai.profile.enums.ESex;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserHealthProfile {

    private Long id;
    private Long userId;

    private ESex sex;

    private Long height;
    private Long weight;

    private LocalDate birthDay;

    private Integer activityId;
    private Integer goalId;
}

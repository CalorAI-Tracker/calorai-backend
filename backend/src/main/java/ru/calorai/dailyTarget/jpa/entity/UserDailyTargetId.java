package ru.calorai.dailyTarget.jpa.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDailyTargetId implements Serializable {

    private Long userId;
    private LocalDate date;
}


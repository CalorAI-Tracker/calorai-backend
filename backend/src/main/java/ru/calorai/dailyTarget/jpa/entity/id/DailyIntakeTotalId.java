package ru.calorai.dailyTarget.jpa.entity.id;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DailyIntakeTotalId implements Serializable {
    private Long userId;
    private LocalDate date;
}

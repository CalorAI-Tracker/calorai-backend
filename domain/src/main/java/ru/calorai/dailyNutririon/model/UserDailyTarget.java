package ru.calorai.dailyNutririon.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserDailyTarget {
    private Long userId;

    private LocalDate date;

    private Integer kcal;

    private BigDecimal proteinG;
    private BigDecimal fatG;
    private BigDecimal carbsG;

    private String source;
}

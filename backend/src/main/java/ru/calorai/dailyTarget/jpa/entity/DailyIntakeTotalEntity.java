package ru.calorai.dailyTarget.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import ru.calorai.dailyTarget.jpa.entity.id.DailyIntakeTotalId;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "daily_intake_totals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@IdClass(DailyIntakeTotalId.class)
public class DailyIntakeTotalEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "kcal", nullable = false)
    private Integer kcal;

    @Column(name = "protein_g", nullable = false, precision = 6, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "fat_g", nullable = false, precision = 6, scale = 2)
    private BigDecimal fatG;

    @Column(name = "carbs_g", nullable = false, precision = 6, scale = 2)
    private BigDecimal carbsG;

    @Column(name = "entries_cnt", nullable = false)
    private Integer entriesCnt;
}



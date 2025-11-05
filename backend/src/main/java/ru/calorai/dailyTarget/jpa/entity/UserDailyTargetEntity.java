package ru.calorai.dailyTarget.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_daily_targets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@IdClass(UserDailyTargetId.class)
public class UserDailyTargetEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "kcal_target")
    private Integer kcalTarget;

    @Column(name = "protein_g_target", precision = 6, scale = 2)
    private Long proteinGTarget;

    @Column(name = "fat_g_target", precision = 6, scale = 2)
    private Long fatGTarget;

    @Column(name = "carbs_g_target", precision = 6, scale = 2)
    private Long carbsGTarget;

    @Column(name = "source")
    private String source;
}

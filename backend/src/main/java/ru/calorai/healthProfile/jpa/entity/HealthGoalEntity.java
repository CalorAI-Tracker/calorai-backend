package ru.calorai.healthProfile.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "health_goal")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HealthGoalEntity {

    @Id
    @Column(name = "id")
    private Short id;

    @Column(name = "code", nullable = false, unique = true, length = 64)
    private String code;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    // сдвиг калорий относительно TDEE
    @Column(name = "calorie_delta_percent", nullable = false)
    private Integer calorieDeltaPercent;
}

package ru.calorai.healthProfile.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "activity_level")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLevelEntity {

    @Id
    @Column(name = "id")
    private Short id;

    @Column(name = "code", nullable = false, unique = true, length = 64)
    private String code;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "factor", nullable = false, precision = 4, scale = 3)
    private BigDecimal factor;
}

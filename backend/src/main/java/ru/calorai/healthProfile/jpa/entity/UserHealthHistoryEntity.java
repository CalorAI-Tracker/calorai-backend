package ru.calorai.healthProfile.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.calorai.healthProfile.jpa.entity.id.HealthHistoryId;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@IdClass(HealthHistoryId.class)
@Entity @Table(name = "health_history")
public class UserHealthHistoryEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Id
    @Column(name = "measured_at", nullable = false)
    private OffsetDateTime measuredAt;

    @Column(name = "weight", nullable = false, precision = 5, scale = 2)
    private Long weight;

    @Column(name = "note")
    private String note;
}
package ru.calorai.profile.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import ru.calorai.user.jpa.entity.UserEntity;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user_profile")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "sex", nullable = false, length = 1)
    private String sex;

    @Column(name = "height_cm", nullable = false)
    private Long height;

    @Column(name = "weight_kg", nullable = false)
    private Long weight;

    @Column(name = "birth_date")
    private LocalDate birthDay;

    @Column(name = "health_goal_id")
    private Long healthGoalId;

    @Column(name = "activity_level_id")
    private Long activityLevelId;

    @Column(name = "target_weight_kg")
    private Long targetWeightKg;

    @Column(name = "weekly_rate_kg")
    private Long weeklyRateKg;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}

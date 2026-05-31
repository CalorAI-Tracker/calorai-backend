package ru.calorai.food.diary.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "food_diary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "food_catalog_id")
    private Long foodCatalogId;

    @Column(name = "eaten_at", nullable = false)
    private LocalDate eatenAt;

    @Column(name = "meal", nullable = false)
    private String meal;

    @Column(name = "quantity_grams", nullable = false, precision = 7, scale = 2)
    private BigDecimal quantityGrams;

    private String note;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;
}

package ru.calorai.foodDiary.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "food_diary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodDiaryEntity {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "eaten_at", nullable = false)
    private LocalDate eatenAt;

    @Column(name = "meal", nullable = false)
    private String meal;

    @Column(name = "entry_name", nullable = false)
    private String entryName;

    private String brand;

    private String barcode;

    @Column(name = "provider_code")
    private String providerCode;

    @Column(name = "provider_food_id")
    private String providerFoodId;

    @Column(name = "quantity_grams", nullable = false, precision = 7, scale = 2)
    private BigDecimal quantityGrams;

    private Integer kcal;

    @Column(name = "protein_g", nullable = false, precision = 6, scale = 2)
    private BigDecimal proteinG;

    @Column(name = "fat_g", nullable = false, precision = 6, scale = 2)
    private BigDecimal fatG;

    @Column(name = "carbs_g", nullable = false, precision = 6, scale = 2)
    private BigDecimal carbsG;

    @Column(name = "fiber_g", precision = 6, scale = 2)
    private BigDecimal fiberG;

    @Column(name = "sugar_g", precision = 6, scale = 2)
    private BigDecimal sugarG;

    @Column(name = "sodium_mg")
    private Integer sodiumMg;

    private String note;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;
}

package ru.calorai.food.catalog.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "food_catalog")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodCatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "barcode")
    private String barcode;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_food_id")
    private String providerFoodId;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "kcal_per_100g", precision = 6, scale = 2)
    private BigDecimal kcalPer100g;

    @Column(name = "protein_per_100g", precision = 6, scale = 2)
    private BigDecimal proteinPer100g;

    @Column(name = "fat_per_100g", precision = 6, scale = 2)
    private BigDecimal fatPer100g;

    @Column(name = "carbs_per_100g", precision = 6, scale = 2)
    private BigDecimal carbsPer100g;

    @Column(name = "fiber_per_100g", precision = 6, scale = 2)
    private BigDecimal fiberPer100g;

    @Column(name = "sugar_per_100g", precision = 6, scale = 2)
    private BigDecimal sugarPer100g;

    @Column(name = "sodium_per_100g", precision = 6, scale = 2)
    private BigDecimal sodiumPer100g;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;
}

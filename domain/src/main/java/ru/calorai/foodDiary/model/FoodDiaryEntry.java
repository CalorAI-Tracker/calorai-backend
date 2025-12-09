package ru.calorai.foodDiary.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder(toBuilder = true)
public class FoodDiaryEntry {
    Long id;
    Long userId;
    LocalDate eatenAt;
    EMeal meal;
    String entryName;
    String brand;
    String barcode;
    String providerCode;
    String providerFoodId;
    Double quantityGrams;
    Integer kcal;
    Double proteinG;
    Double fatG;
    Double carbsG;
    Double fiberG;
    Double sugarG;
    Integer sodiumMg;
    String note;
}

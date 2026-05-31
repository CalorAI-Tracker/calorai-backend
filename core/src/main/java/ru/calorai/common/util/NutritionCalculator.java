package ru.calorai.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class NutritionCalculator {

    private NutritionCalculator() {}

    /**
     * Пересчитывает значение с базового объёма на 100г
     */
    public static BigDecimal per100g(Double valuePerBase, double base) {
        if (valuePerBase == null || base == 0) return null;
        return BigDecimal.valueOf(valuePerBase * 100.0 / base)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Считает ккал на 100г из БЖУ на 100г
     */
    public static BigDecimal calcKcalPer100g(BigDecimal protein, BigDecimal fat, BigDecimal carbs) {
        if (protein == null || fat == null || carbs == null) return null;
        double kcal = protein.doubleValue() * 4
                + fat.doubleValue() * 9
                + carbs.doubleValue() * 4;
        return BigDecimal.valueOf(kcal).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Считает КБЖУ для фактической порции из значений на 100г
     */
    public static BigDecimal forPortion(BigDecimal per100g, BigDecimal portionGrams) {
        if (per100g == null || portionGrams == null) return null;
        return per100g.multiply(portionGrams)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }

    /**
     * Считает ккал для порции из БЖУ для порции
     */
    public static int calcKcal(BigDecimal protein, BigDecimal fat, BigDecimal carbs) {
        if (protein == null || fat == null || carbs == null) return 0;
        double kcal = protein.doubleValue() * 4
                + fat.doubleValue() * 9
                + carbs.doubleValue() * 4;
        return (int) Math.round(kcal);
    }
}

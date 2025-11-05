package ru.calorai.dailyNutririon.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class DailyNutrition {

    private Long userId;

    private LocalDate date;

    private Macro plan;
    private Macro actual;

    public Macro remaining() {
        return plan.minus(actual);
    }

    public Map<String, Integer> completionPercent() {
        Map<String, Integer> m = new HashMap<>();

        m.put("kcal", percent(actual.getKcal(), plan.getKcal()));
        m.put("protein", percent(actual.getProteinG(), plan.getProteinG()));
        m.put("fat", percent(actual.getFatG(), plan.getFatG()));
        m.put("carbs", percent(actual.getCarbsG(), plan.getCarbsG()));

        return m;
    }

    private static int percent(int actual, int plan) {
        if (plan <= 0) return 0;
        var p = Math.round((actual * 100.0f) / plan);
        return (int) Math.max(0, p);
    }

    private static int percent(BigDecimal actual, BigDecimal plan) {
        if (plan == null || plan.signum() == 0) return 0;
        var p = actual.multiply(BigDecimal.valueOf(100))
                .divide(plan, java.math.RoundingMode.HALF_UP);
        return p.intValue();
    }
}

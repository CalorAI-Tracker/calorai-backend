package ru.calorai.dailyNutririon.model;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Macro {

    private Integer kcal;

    private BigDecimal proteinG;
    private BigDecimal fatG;
    private BigDecimal carbsG;

    public static Macro zero() {
        return new Macro(0,
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2),
                BigDecimal.ZERO.setScale(2));
    }

    public Macro minus(Macro other) {
        return Macro.builder()
                .kcal(this.kcal - other.kcal)
                .proteinG(this.proteinG.subtract(other.proteinG))
                .fatG(this.fatG.subtract(other.fatG))
                .carbsG(this.carbsG.subtract(other.carbsG))
                .build();
    }
}

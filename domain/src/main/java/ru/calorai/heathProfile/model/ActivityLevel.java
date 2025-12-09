package ru.calorai.heathProfile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLevel {

    private Short id;

    private String code;
    private String title;
    private String description;
    private BigDecimal factor;
}

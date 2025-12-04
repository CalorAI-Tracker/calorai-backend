package ru.calorai.healthProfile.jpa.entity.id;

import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class HHistoryId implements Serializable {
    private Long userId;
    private OffsetDateTime measuredAt;
}

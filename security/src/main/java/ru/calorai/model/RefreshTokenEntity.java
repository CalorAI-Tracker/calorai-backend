package ru.calorai.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Clock;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
@Builder(toBuilder = true)
@NoArgsConstructor @AllArgsConstructor
public class RefreshTokenEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "uuid")
    private UUID jti;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, length = 255)
    private String tokenHash;

    @Column(nullable = false)
    private OffsetDateTime issuedAt;

    @Column(nullable = false)
    private OffsetDateTime expiresAt;

    @Column(nullable = false)
    @Builder.Default
    private Boolean revoked = false;

    @Column(columnDefinition = "uuid")
    private UUID replacedByJti;

    private String deviceId;
    private String userAgent;
    private String ipAddress;

    public boolean isExpired(Clock clock) {
        return OffsetDateTime.now(clock).isAfter(expiresAt);
    }
}

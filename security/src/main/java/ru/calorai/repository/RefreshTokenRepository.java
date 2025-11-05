package ru.calorai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.calorai.model.RefreshTokenEntity;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, Long> {
    Optional<RefreshTokenEntity> findByJti(UUID jti);
    @Modifying
    @Query("""
        update RefreshTokenEntity t
        set t.revoked = true
        where t.user.id = :userId and t.revoked = false
    """)
    int revokeAllByUserId(@Param("userId") Long userId);
}

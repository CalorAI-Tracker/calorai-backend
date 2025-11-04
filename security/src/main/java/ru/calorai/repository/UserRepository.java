package ru.calorai.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.calorai.model.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findByEmail(String email);

    @EntityGraph(attributePaths = {"roles"})
    Optional<UserEntity> findById(Long id);
}

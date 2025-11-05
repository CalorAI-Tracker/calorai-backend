package ru.calorai.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String name;

    private String email;

    private String passwordHash ;

    private Boolean emailVerified;

    private String authProvider;

    private String providerId;

    private OffsetDateTime createdAt;

    private OffsetDateTime lastLoginAt;

    private Boolean enabled;

    private Set<Long> roles = new HashSet<>();
}

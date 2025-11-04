package ru.calorai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.calorai.dto.request.LoginRequest;
import ru.calorai.dto.request.SignUpRequest;
import ru.calorai.dto.response.TokenResponse;
import ru.calorai.jwToken.JwtProperties;
import ru.calorai.model.AuthProvider;
import ru.calorai.model.UserEntity;
import ru.calorai.repository.RoleRepository;
import ru.calorai.repository.UserRepository;
import ru.calorai.service.JwtService;
import ru.calorai.service.RefreshTokenService;
import ru.calorai.users.ERole;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Tag(name = "Аутентификация", description = "Вход, обновление и отзыв JWT токенов")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtProperties jwtProperties;

    @Autowired
    JwtService jwt;

    @Autowired
    RefreshTokenService refreshTokens;

    @Autowired
    UserRepository users;

    @Autowired
    RoleRepository roles;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody @Valid LoginRequest req,
                               HttpServletRequest http) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.email(), req.password())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        UserEntity user = users.findByEmail(req.email()).orElseThrow();

        var authorities = roles.findByUsersId(user.getId()).stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .toList();

        String access = jwt.generateAccessToken(user.getEmail(), user.getId(), authorities);

        String refresh = refreshTokens.issue(
                user.getId(),
                req.deviceId(),
                http.getHeader("User-Agent"),
                http.getRemoteAddr()
        );

        long expiresIn = jwtProperties.getAccessTokenExpirationMinutes() * 60L;
        return new TokenResponse(access, refresh, "Bearer", expiresIn);
    }

    @PostMapping("/refresh")
    public Map<String, String> refresh(@RequestBody Map<String, String> body) {
        String refresh = body.get("refreshToken");
        if (!jwt.validateRefreshToken(refresh)) {
            throw new BadCredentialsException("invalid refresh");
        }
        Pair<String, String> pair = refreshTokens.rotate(
                refresh,
                userId -> roles.findByUsersId(userId).stream()
                        .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                        .collect(Collectors.toList()),
                (email, userId) -> {
                    Collection<? extends GrantedAuthority> auths =
                            roles.findByUsersId(userId).stream()
                                    .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                                    .collect(Collectors.toList());
                    return jwt.generateAccessToken(email, userId, auths);
                }
        );
        return Map.of(
                "accessToken", pair.getFirst(),
                "refreshToken", pair.getSecond(),
                "tokenType", "Bearer"
        );
    }

    @PostMapping("/signup")
    @Operation(summary = "Регистрация локального пользователя (email+пароль)")
    public ResponseEntity<?> register(@RequestBody @Valid SignUpRequest req) {

        if (users.findByEmail(req.email()).isPresent()) {
            return ResponseEntity.status(409).body(Map.of("error", "EMAIL_TAKEN"));
        }

        var user = UserEntity.builder()
                .email(req.email())
                .passwordHash(passwordEncoder.encode(req.password()))
                .authProvider(AuthProvider.LOCAL.name())
                .emailVerified(false)
                .enabled(true)
                .createdAt(java.time.OffsetDateTime.now())
                .lastLoginAt(null)
                .roles(new java.util.HashSet<>())
                .build();

        roles.findByName(ERole.USER.name()).ifPresent(user.getRoles()::add);

        users.save(user);

        return ResponseEntity.status(201).body(Map.of(
                "id", user.getId(),
                "email", user.getEmail()
        ));
    }

    @PostMapping("/logout")
    public void logout(@RequestBody Map<String, String> body) {
        String refresh = body.getOrDefault("refreshToken", "");
        if (jwt.validateRefreshToken(refresh)) {
            refreshTokens.revokeByJti(jwt.extractJtiFromRefresh(refresh));
        }
    }

//    @PostMapping("/logout-all")
//    @PreAuthorize("isAuthenticated()")
//    public void logoutAll(Authentication auth) {
//        users.findByEmail(auth.getName())
//                .ifPresent(u -> refreshTokens.revokeAllForUser(u.getId()));
//    }
}

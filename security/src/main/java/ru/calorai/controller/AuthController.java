package ru.calorai.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ru.calorai.service.JwtService;
import ru.calorai.service.RefreshTokenService;
import ru.calorai.user.jpa.entity.UserEntity;
import ru.calorai.user.jpa.repository.RoleRepository;
import ru.calorai.user.jpa.repository.UserRepository;
import ru.calorai.profile.enums.ERole;
import ru.calorai.users.exception.EmailAlreadyTakenException;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
@Tag(name = "Аутентификация", description = "Вход, обновление и отзыв JWT токенов")
public class AuthController {

    @Autowired AuthenticationManager authenticationManager;

    @Autowired JwtService jwt;

    @Autowired JwtProperties jwtProperties;
    @Autowired RefreshTokenService refreshTokens;

    @Autowired UserRepository users;
    @Autowired RoleRepository roles;

    @Autowired PasswordEncoder passwordEncoder;

    @Operation(
            summary = "Вход в систему",
            description = "Аутентификация пользователя по email и паролю, возвращает access и refresh токены")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Успешная аутентификация",
            content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные"),
            @ApiResponse(responseCode = "400", description = "Невалидные данные запроса")
    })
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

    @Operation(
            summary = "Обновление токенов",
            description = "Обновление access токена с помощью refresh токена")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "Токены успешно обновлены",
            content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "401", description = "Невалидный refresh токен")
    })
    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody Map<String, String> body) {
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
        return new TokenResponse(
                pair.getFirst(),
                 pair.getSecond(),
                "Bearer",
                0L
        );
    }

    @Operation(
            summary = "Регистрация пользователя",
            description = "Создание нового пользователя с email и паролем")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь успешно зарегистрирован",
                    content = @Content(schema = @Schema(implementation = SignUpRequest.class))),
            @ApiResponse(responseCode = "400", description = "Невалидные данные запроса")
    })
    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody @Valid SignUpRequest req) {

        if (users.findByEmail(req.email()).isPresent()) {
            throw new EmailAlreadyTakenException();
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

    @Operation(summary = "Выход из системы", description = "Отзыв refresh токена")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный выход"),
            @ApiResponse(responseCode = "400", description = "Невалидный токен")})
    @PostMapping("/logout")
    public void logout(@RequestBody Map<String, String> body) {
        String refresh = body.getOrDefault("refreshToken", "");
        if (jwt.validateRefreshToken(refresh)) {
            refreshTokens.revokeByJti(jwt.extractJtiFromRefresh(refresh));
        }
    }

    /*
    @Operation(
        summary = "Выход со всех устройств",
        description = "Отзыв всех refresh токенов пользователя"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Успешный выход со всех устройств"),
        @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    @PostMapping("/logout-all")
    @PreAuthorize("isAuthenticated()")
    public void logoutAll(
            @Parameter(description = "Аутентификация пользователя", hidden = true)
            Authentication auth) {

        users.findByEmail(auth.getName())
                .ifPresent(u -> refreshTokens.revokeAllForUser(u.getId()));
    }
    */
}

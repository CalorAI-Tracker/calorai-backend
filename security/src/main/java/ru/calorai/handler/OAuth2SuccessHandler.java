package ru.calorai.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.calorai.jwToken.JwtProperties;
import ru.calorai.service.JwtService;
import ru.calorai.service.RefreshTokenService;
import ru.calorai.user.jpa.entity.UserEntity;
import ru.calorai.user.jpa.repository.UserRepository;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Lazy
    private final RefreshTokenService refreshTokenService;

    private final UserRepository userRepository;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OidcUser principal = (OidcUser) authentication.getPrincipal();

        String email = principal.getEmail();
        // достанем id пользователя из БД (не полагаемся на «добавленные» атрибуты)
        UserEntity user = userRepository.findByEmail(email).orElseThrow();
        Long userId = user.getId();

        var authorities = authentication.getAuthorities();

        String accessToken = jwtService.generateAccessToken(email, userId, authorities);

        String refreshToken = refreshTokenService.issue(
                userId,
                null,
                request.getHeader("User-Agent"),
                request.getRemoteAddr()
        );

        long expiresInSeconds = jwtProperties.getAccessTokenExpirationMinutes() * 60L;

        var tokenResponse = Map.of(
                "accessToken", accessToken,
                "refreshToken", refreshToken,
                "tokenType", "Bearer",
                "expiresIn", String.valueOf(expiresInSeconds)
        );

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());
        objectMapper.writeValue(response.getWriter(), tokenResponse);
    }
}

package ru.calorai.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ru.calorai.jwToken.JwtProperties;
import ru.calorai.service.JwtService;
import ru.calorai.service.RefreshTokenService;

import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private final JwtProperties jwtProperties;

    @Lazy
    private final RefreshTokenService refreshTokenService;

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

        String email = oauth2User.getAttribute("email");
        Long userId  = oauth2User.getAttribute("userId");
        var authorities = authentication.getAuthorities();

        // access как и раньше
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

package ru.calorai.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.calorai.filter.JwtAuthFilter;
import ru.calorai.handler.OAuth2SuccessHandler;
import ru.calorai.service.OidcUserServiceImpl;

import java.util.List;

/**
 * Конфигурация безопасности веб-приложения.
 * Включает настройку цепочки фильтров безопасности, аутентификации и авторизации.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EntityScan("ru.calorai")
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationBean authenticationBean;

    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final OidcUserServiceImpl oidcUserService;

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(a -> a
                        .requestMatchers(
                                "/oauth2/**", "/login/oauth2/**", "/auth/**",
                                "/swagger-ui/**", "/v3/api-docs/**", "/actuator/health"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationBean.daoAuthProvider())

                .oauth2Login(o -> o
                        .userInfoEndpoint(ui -> ui.oidcUserService(oidcUserService))
                        .successHandler(oAuth2SuccessHandler)
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

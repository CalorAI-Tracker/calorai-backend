package ru.calorai.config;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain security(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/error",
                                "/oauth2/**", "/login/**",
                                "/config/health", "/config/info",
                                "/actuator/health", "/actuator/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(o -> o.defaultSuccessUrl("/", true))
                .logout(l -> l.logoutSuccessUrl("/"));
        return http.build();
    }
}
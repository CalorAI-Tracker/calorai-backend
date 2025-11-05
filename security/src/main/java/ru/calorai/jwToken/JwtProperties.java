package ru.calorai.jwToken;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String issuer;

    private String accessTokenSecret;

    private String refreshTokenSecret;

    private Integer refreshTokenExpirationDays;

    private Integer accessTokenExpirationMinutes;
}

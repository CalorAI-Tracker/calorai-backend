package ru.calorai.jwToken.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.JWTVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtVerifierConfig {

    @Bean("accessVerifier")
    JWTVerifier accessTokenVerifier(@Qualifier("accessAlgorithm") Algorithm alg,
                                    @Value("${jwt.issuer}") String issuer) {
        return JWT.require(alg).withIssuer(issuer).withClaim("type", "access").build();
    }

    @Bean("refreshVerifier")
    JWTVerifier refreshTokenVerifier(@Qualifier("refreshAlgorithm") Algorithm alg,
                                     @Value("${jwt.issuer}") String issuer) {
        return JWT.require(alg).withIssuer(issuer).withClaim("type", "refresh").build();
    }
}

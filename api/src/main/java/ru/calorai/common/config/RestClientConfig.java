package ru.calorai.common.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;
import ru.calorai.common.config.properties.LogMealProperties;
import ru.calorai.common.config.properties.OpenFoodFactProperties;

@Configuration
@EnableConfigurationProperties({LogMealProperties.class, OpenFoodFactProperties.class})
public class RestClientConfig {

    @Bean(name = "openFoods")
    @Primary
    public RestClient openFoodsFactrestClient(OpenFoodFactProperties props) {
        return RestClient.builder()
                .baseUrl(props.baseUrl())
                .build();
    }

    @Bean(name = "logMeal")
    public RestClient logMealRestClient(LogMealProperties props) {
        return RestClient.builder()
                .baseUrl(props.baseUrl())
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + props.token())
                .build();
    }
}

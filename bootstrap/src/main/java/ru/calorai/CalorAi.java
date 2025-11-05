package ru.calorai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Главный класс приложения CalorAI Module
 */
@SpringBootApplication
@ConfigurationPropertiesScan("ru.calorai")
public class CalorAi {
    
    public static void main(String[] args) {
        SpringApplication.run(CalorAi.class, args);
    }
}
package ru.calorai.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для логирования и свойств приложения
 */
@Configuration
@EnableConfigurationProperties(AppConfigProperties.class)
public class LoggingConfig {
    
    // Дополнительная конфигурация логирования может быть добавлена здесь
    
}

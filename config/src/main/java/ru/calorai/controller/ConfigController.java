package ru.calorai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.calorai.config.AppConfigProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * REST контроллер для конфигурации
 */
@RestController
@RequestMapping("/config")
public class ConfigController {
    
    @Autowired
    private AppConfigProperties appConfigProperties;
    
    /**
     * Получить информацию о конфигурации приложения
     */
    @GetMapping("/info")
    public Map<String, Object> getConfigInfo() {
        Map<String, Object> configInfo = new HashMap<>();
        configInfo.put("version", appConfigProperties.getVersion());
        configInfo.put("description", appConfigProperties.getDescription());
        configInfo.put("environment", appConfigProperties.getEnvironment());
        configInfo.put("debug", appConfigProperties.isDebug());
        configInfo.put("timestamp", System.currentTimeMillis());
        
        return configInfo;
    }
    
    /**
     * Проверка здоровья приложения
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "config");
        return health;
    }
}

# CalorAI Config Module

Модуль конфигурации для приложения CalorAI на базе Spring Boot.

## Возможности

- ✅ Spring Boot 3.2.0
- ✅ YAML конфигурация с профилями (dev, prod)
- ✅ REST API для получения информации о конфигурации
- ✅ Автоматическая перезагрузка в режиме разработки
- ✅ Структурированное логирование

## Структура проекта

```
config/
├── src/main/java/ru/calorai/
│   ├── Main.java                    # Главный класс приложения
│   ├── config/
│   │   ├── AppConfigProperties.java # Свойства конфигурации
│   │   └── LoggingConfig.java       # Настройки логирования
│   └── controller/
│       └── ConfigController.java    # REST контроллер
└── src/main/resources/
    ├── application.yml              # Основная конфигурация
    ├── application-dev.yml          # Конфигурация для разработки
    └── application-prod.yml         # Конфигурация для продакшена
```

## Запуск приложения

### Режим разработки (по умолчанию)
```bash
mvn spring-boot:run
```

### Режим продакшена
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

### Сборка JAR файла
```bash
mvn clean package
java -jar target/config-1.0-SNAPSHOT.jar
```

## API Endpoints

После запуска приложения доступны следующие endpoints:

- `GET /api/config/info` - Информация о конфигурации приложения
- `GET /api/config/health` - Проверка здоровья сервиса

## Конфигурация

Основные настройки находятся в файлах:
- `application.yml` - базовые настройки
- `application-dev.yml` - для разработки
- `application-prod.yml` - для продакшена

## Порт по умолчанию

Приложение запускается на порту **8080** с контекстным путем `/api`.

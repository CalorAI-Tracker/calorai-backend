# Запуск проекта CalorAI

## 1. Сборка проекта

Перед первым запуском собери проект через Gradle Wrapper:

```bash
# Linux / macOS
./gradlew clean build

# Windows PowerShell / CMD
.\gradlew clean build
```

Если всё прошло успешно — в папке `config/build/libs` появится `.jar`-файл.

---

## 2. Настройка конфигурации запуска в IntelliJ IDEA

1. Открой меню:
   ```
   Run → Edit Configurations… → + → Gradle
   ```
2. Укажи параметры:
   ```
   Name: bootRun (config)
   Gradle project: calorai-backend
   Tasks: :config:bootRun
   Use Gradle from: gradle-wrapper
   Working directory: <путь до корня проекта>
   ```
3. Нажми **Apply → Run** ✅

---

## 3. Проверка работы

После запуска открой в браузере:

```
http://localhost:8080/api/config/health
```

Ожидаемый ответ:

```json
{
  "status": "UP",
  "service": "config"
}
```

---

## 4. Альтернатива — поднятие docker контейнера

Создать .env файлd в корне проекта с необходимыми токенами (Google Secret, LogMeal API token). Пример:
```
GOOGLE_CLIENT_ID=SECRET_KEY12233
GOOGLE_CLIENT_SECRET=CLIENT_SECRET_KEY12233
LOG_MEAL_API_TOKEN=API_TOKE_112121
```

Запустить докер:

```docker compose down```

```docker volume rm calorai-backend_calorai_data```

```docker compose up --build```

После этого приложение будет доступно по адресу  
👉 [http://localhost:8080/api/config/health](http://localhost:8080/api/config/health)

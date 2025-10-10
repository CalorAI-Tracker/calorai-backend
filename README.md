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
3. Нажми на **Environment variables** и добавь переменные окружения:
   ```
   SPRING_PROFILES_ACTIVE=dev;
   GOOGLE_CLIENT_ID=xxx.apps.googleusercontent.com;
   GOOGLE_CLIENT_SECRET=xxxx;
   JWT_SECRET=change_me_long_random;
   ```
   > Эти переменные нужны для запуска приложения и авторизации через Google OAuth2.
4. Нажми **Apply → Run** ✅

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

## 4. Альтернатива — запуск из терминала

```bash
# Linux / macOS
SPRING_PROFILES_ACTIVE=dev \
GOOGLE_CLIENT_ID=xxx.apps.googleusercontent.com \
GOOGLE_CLIENT_SECRET=xxxx \
JWT_SECRET=change_me_long_random \
./gradlew :config:bootRun

# Windows PowerShell
$env:SPRING_PROFILES_ACTIVE="dev"
$env:GOOGLE_CLIENT_ID="xxx.apps.googleusercontent.com"
$env:GOOGLE_CLIENT_SECRET="xxxx"
$env:JWT_SECRET="change_me_long_random"
.\gradlew :config:bootRun
```

После этого приложение будет доступно по адресу  
👉 [http://localhost:8080/api/config/health](http://localhost:8080/api/config/health)

FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app

# Копируем весь мульти-модульный проект
COPY . .

# Переходим в модуль с Spring Boot и собираем jar
WORKDIR /app/bootstrap
RUN ./gradlew bootJar --no-daemon

# -------------------- RUNTIME-образ --------------------
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Копируем собранный jar из модуля bootstrap
COPY --from=builder /app/bootstrap/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

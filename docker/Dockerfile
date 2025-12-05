FROM gradle:9.1.0-jdk21 AS builder

ENV GRADLE_OPTS="-Dorg.gradle.daemon=false -Dorg.gradle.parallel=true -Dorg.gradle.configureondemand=true"

WORKDIR /app

# 1. Корневые Gradle файлы — копируем первыми
COPY settings.gradle.kts build.gradle.kts ./
COPY gradle ./gradle
COPY gradlew ./gradlew

# 2. Чиним Windows CRLF и ставим +x
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# 3. Копируем только build.gradle.kts подмодулей (кеш зависимостей)
COPY bootstrap/build.gradle.kts bootstrap/
COPY api/build.gradle.kts       api/
COPY backend/build.gradle.kts   backend/
COPY core/build.gradle.kts      core/
COPY domain/build.gradle.kts    domain/
COPY security/build.gradle.kts  security/

# 4. Прогреваем зависимости
RUN ./gradlew :bootstrap:dependencies --no-daemon || true

# 5. Теперь копируем весь код
COPY . .

# 6. Ещё раз чиним gradlew, потому что COPY . . Мог перетереть файл
RUN sed -i 's/\r$//' gradlew && chmod +x gradlew

# 7. Сборка bootJar
RUN ./gradlew :bootstrap:bootJar --no-daemon -x test

# ---------- runtime-образ ----------
FROM gcr.io/distroless/java21

WORKDIR /app

COPY --from=builder /app/bootstrap/build/libs/*.jar app.jar

EXPOSE 8080
USER nonroot

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]

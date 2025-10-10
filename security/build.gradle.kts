plugins {
    `java-library`
    id("io.spring.dependency-management")
}

dependencies {
    // BOM как в твоём parent (3.5.3)
    api(platform("org.springframework.boot:spring-boot-dependencies:3.5.3"))

    // из твоего pom
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")

    implementation("io.jsonwebtoken:jjwt-api:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}

// обычный jar (bootJar здесь не нужен)
tasks.named<Jar>("jar") { enabled = true }

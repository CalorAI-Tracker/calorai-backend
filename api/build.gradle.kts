plugins {
    id("java")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":backend"))

    // Spring Boot
    implementation(libs.spring.boot.security)
    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.validation)

    implementation(libs.spring.boot.jpa)
    implementation(libs.spring.boot.jdbc)

    // Swagger
    implementation(libs.springdoc.openapi)

    // JWT
    implementation(libs.auth0.jwt)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // MapStruct
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)

    // Commons
    implementation(libs.commons.io)
}

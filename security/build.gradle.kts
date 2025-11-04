plugins {
    id("java")
}

dependencies {
    implementation(project(":domain"))

    // Spring Security
    implementation(libs.spring.boot.security)
    implementation(libs.spring.security.oauth2.client)
//    implementation(libs.spring.security.oauth2.jose)

    // Spring
    implementation(libs.spring.boot.web)
    implementation(libs.spring.boot.validation)
    implementation(libs.spring.boot.jpa)

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

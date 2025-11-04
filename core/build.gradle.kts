dependencies {
    implementation(project(":domain"))

    // Spring Boot
    implementation(libs.spring.boot.starter)
    implementation(libs.spring.boot.jpa)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Location-tech
    implementation(libs.locationtech.jts.core)
}
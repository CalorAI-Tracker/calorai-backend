dependencies {

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // Spring
    implementation(libs.spring.boot.starter)

    // Jooq
    implementation(libs.jooq)
    implementation(libs.jooq.codegen)

    // Location-tech
    implementation(libs.locationtech.jts.core)
}
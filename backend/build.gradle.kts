dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))

    // Spring
    implementation(libs.spring.boot.jpa)
    implementation(libs.spring.boot.jdbc)
    implementation(libs.spring.boot.validation)

    // JDBC driver
    implementation(libs.postgresql.driver)

    // Lombok
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)

    // MapStruct
    implementation(libs.mapstruct)
    annotationProcessor(libs.mapstruct.processor)

    // Jooq
    implementation(libs.jooq)
    implementation(libs.jooq.codegen)

    // Hibernate
    implementation(libs.hibernate.spatial)
}
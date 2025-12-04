plugins {
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(project(":backend"))
    implementation(project(":core"))
    implementation(project(":api"))
    implementation(project(":security"))
    implementation(project(":domain"))

    implementation(libs.spring.boot.starter)
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}
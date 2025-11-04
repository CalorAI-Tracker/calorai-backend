plugins {
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(project(":backend"))
    implementation(project(":core"))
    implementation(project(":api"))

    implementation(libs.spring.boot.starter)
}
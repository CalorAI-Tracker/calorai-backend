plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}
rootProject.name = "calorai-backend"

dependencyResolutionManagement {
    repositoriesMode = RepositoriesMode.PREFER_PROJECT
    repositories {
        mavenCentral()
    }
}

include(
    "bootstrap",
    "domain",
    "api",
    "core",
    "backend",
    "security"
)

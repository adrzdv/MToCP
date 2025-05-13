pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.android.application") version "8.1.4"
        id("com.android.library") version "8.1.4"
        kotlin("android") version "1.9.0"
        kotlin("kapt") version "1.9.0"
        id("dagger.hilt.android.plugin") version "2.48"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MToCP"
include(":app")

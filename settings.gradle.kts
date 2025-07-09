pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        id("com.android.application") version "8.11.0"
        id("com.android.library") version "8.11.0"
        kotlin("android") version "2.1.10"
        kotlin("kapt") version "2.1.10"
        id("org.jetbrains.kotlin.plugin.compose") version "2.1.10"
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

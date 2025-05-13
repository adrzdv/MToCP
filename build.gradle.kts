// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {

        val hiltVersion = "2.48"
        val kotlinPlugin = "1.9.0"

        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinPlugin")
        classpath("com.google.dagger:hilt-android-gradle-plugin:$hiltVersion")
    }
}

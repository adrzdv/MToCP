import com.android.build.api.dsl.ApplicationExtension
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.compose)
}


extensions.configure<ApplicationExtension> {
    namespace = "com.adrzdv.mtocp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.adrzdv.mtocp"
        minSdk = 30
        targetSdk = 35
        versionCode = 1
        versionName = "0.9.6-beta-rb-01112025"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

kotlin {
    jvmToolchain(17)
}

kapt {
    correctErrorTypes = true
}

dependencies {

    //implementation(libs.androidx.navigation.compose.jvmstubs)
    implementation("androidx.navigation:navigation-compose:2.9.5")
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.room.runtime.android)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui.graphics)
    implementation("androidx.fragment:fragment-ktx:1.8.9")
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    implementation(libs.room.ktx)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    val composeBom = platform("androidx.compose:compose-bom:2024.09.00")
    implementation(composeBom)

    implementation("androidx.activity:activity-compose")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material3:material3")

    implementation("androidx.compose.material3:material3:1.4.0-alpha14")
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.10.1")

    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation(libs.androidx.runtime.android)
    implementation(libs.androidx.foundation.android)
    implementation(libs.androidx.material3.android)
    val roomVersion = "2.8.2"

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation("androidx.room:room-runtime:$roomVersion")

    testImplementation(libs.junit)

    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    kapt("androidx.room:room-compiler:$roomVersion")

    implementation("com.google.code.gson:gson:2.13.2")

    implementation("net.lingala.zip4j:zip4j:2.11.5")

    implementation("com.squareup.okhttp3:okhttp:5.2.1")
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")

}
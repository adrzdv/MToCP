import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.android")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.adrzdv.mtocp"
    compileSdk = 36

    val localProperties = Properties()
    val localPropertiesFile = rootProject.file("local.properties")
    val keystoreProperties = Properties()
    val keystorePropertiesFile = rootProject.file("keystore.properties")

    if (keystorePropertiesFile.exists()) {
        keystoreProperties.load(FileInputStream(keystorePropertiesFile))
    }

    if (localPropertiesFile.exists()) {
        localProperties.load(FileInputStream(localPropertiesFile))
    }

    defaultConfig {
        applicationId = "com.adrzdv.mtocp"
        minSdk = 29                                                 //Android 10+
        targetSdk = 35
        versionCode = 1
        versionName = "0.9.8-beta-hf-01042026"

        buildConfigField("String", "BASE_URL", "\"${project.findProperty("BASE_URL") ?: ""}\"")
        buildConfigField("String", "UPDATE_URL", "\"${project.findProperty("UPDATE_URL") ?: ""}\"")


        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {

            val isCi = System.getenv("CI") == "true"

            if (isCi) {

                val storeFilePath = System.getenv("KEYSTORE_FILE")
                    ?: throw GradleException("KEYSTORE_FILE is missing in CI")

                val storePassword = System.getenv("KEYSTORE_PASSWORD")
                    ?: throw GradleException("KEYSTORE_PASSWORD is missing")

                val keyAlias = System.getenv("KEY_ALIAS")
                    ?: throw GradleException("KEY_ALIAS is missing")

                val keyPassword = System.getenv("KEY_PASSWORD")
                    ?: throw GradleException("KEY_PASSWORD is missing")

                val fileRef = file(storeFilePath)
                if (!fileRef.exists()) {
                    throw GradleException("Keystore file not found: $storeFilePath")
                }

                storeFile = fileRef
                this.storePassword = storePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword

            } else {

                val propsFile = rootProject.file("keystore.properties")

                if (!propsFile.exists()) {
                    throw GradleException("keystore.properties is missing for local build")
                }

                val props = Properties().apply {
                    load(FileInputStream(propsFile))
                }

                storeFile = file(props["storeFile"].toString())
                storePassword = props["storePassword"].toString()
                keyAlias = props["keyAlias"].toString()
                keyPassword = props["keyPassword"].toString()
            }
        }
    }


    kotlinOptions {
        jvmTarget = "17"
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.13"
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true
    }
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
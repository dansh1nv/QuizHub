plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "ru.quizHub.settings"
    compileSdk = 36

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
     
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlin.coroutines.android)

    implementation(libs.bundles.common)
    implementation(libs.serialization)

    // ksp(libs.ksp)

    implementation(project(":core"))
    implementation(project(":database"))
    implementation(project(":domain:settings"))
}
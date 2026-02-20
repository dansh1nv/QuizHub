plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
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
        sourceCompatibility =  JavaVersion.VERSION_21
        targetCompatibility =  JavaVersion.VERSION_21
    }
}

dependencies {
    implementation(libs.bundles.common)
    implementation(libs.kotlin.coroutines.android)
    testImplementation(libs.junit)
}
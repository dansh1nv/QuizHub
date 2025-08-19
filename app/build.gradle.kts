plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)

}

android {
    namespace = "ru.quizHub.quizhub"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.quizHub.quizhub"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    kotlinOptions {
        jvmTarget = "21"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.common)
    implementation(libs.androidx.splashscreen)
    implementation(libs.location)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.datastore.preferences)

    implementation(project(":core"))
    implementation(project(":designSystem"))
    implementation(project(":common"))
    implementation(project(":database"))
    implementation(project(":quizapi"))
    implementation(project(":data:quizList"))
    implementation(project(":domain:quizList"))
    implementation(project(":features:quizList"))
    implementation(project(":features:quizDetails"))

    debugImplementation(libs.leak.canary)
}
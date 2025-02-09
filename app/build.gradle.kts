plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "ru.dansh1nv.quizhub"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.dansh1nv.quizhub"
        minSdk = 26
        targetSdk = 34
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
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.common)
    implementation(libs.bundles.navigation)

    implementation(libs.kotlin.coroutines.android)
    implementation(libs.androidx.ui.text.google.fonts)

    implementation(project(":core"))
    implementation(project(":designSystem"))
    implementation(project(":common"))
    implementation(project(":navigation"))
    //ksp(libs.koin.)

    implementation(project(":database"))
    implementation(project(":quizapi"))
    implementation(project(":data-quiz-list"))
    implementation(project(":domain-quiz-list"))
    implementation(project(":features:quiz-list"))
    implementation(project(":features:quiz-details"))
    debugImplementation(libs.leak.canary)
}
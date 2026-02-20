plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "ru.quizHub.core"
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
    implementation(libs.bundles.common)
    implementation(libs.bundles.ui)
    implementation(libs.calendar.compose)
    implementation(libs.serialization)
    implementation(libs.datastore.core)
    implementation(libs.datastore.preferences)

    api(project(":designSystem"))
}
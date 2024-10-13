plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

configurateAndroid()

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.bundles.ui)
    implementation(libs.bundles.common)
    implementation(libs.bundles.navigation)

    implementation(project(":domain-quiz-list"))
    implementation(project(":common"))
    implementation(project(":designSystem"))
    implementation(project(":navigation"))
    implementation(project(":core"))
}

//Разбить на функции и сделать конфиги для других модулей
fun Project.configurateAndroid() {
    android {
        namespace = "ru.dansh1nv.quiz.list"
        compileSdk = 34

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
        buildFeatures {
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = "1.5.8"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_21
            targetCompatibility = JavaVersion.VERSION_21
        }
        kotlinOptions {
            jvmTarget = "21"
        }
    }
}
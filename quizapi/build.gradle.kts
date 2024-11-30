plugins {
    alias(libs.plugins.org.jetbrains.kotlin.jvm)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {

    implementation(libs.kotlin.coroutines.android)

    implementation(libs.serialization)
    implementation(libs.jsoup)
    implementation(libs.bundles.network)

    implementation(libs.koin.core)
    implementation(libs.koin.annotations)

    implementation(project(":core"))

    //ksp(libs.ksp)
}
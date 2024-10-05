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

    implementation(libs.ktor.client)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.client.okhttp)

    implementation(libs.koin.core)
    implementation(libs.koin.annotations)

    //ksp(libs.ksp)
}
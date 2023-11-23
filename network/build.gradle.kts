plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

dependencies {
    implementation(projects.storage)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.auth)
}
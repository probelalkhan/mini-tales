plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

dependencies {
    implementation(projects.network)
    implementation(libs.ktor.serialization)
    implementation(libs.javax.inject)
}
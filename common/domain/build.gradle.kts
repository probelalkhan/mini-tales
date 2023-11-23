plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
    id(libs.plugins.kotlin.serialization.get().pluginId)
}

dependencies {
    implementation(projects.storage)
    implementation(projects.common.data)
    implementation(projects.common.utils)
    implementation(projects.network)
    implementation(libs.ktor.serialization)
    implementation(libs.javax.inject)
}
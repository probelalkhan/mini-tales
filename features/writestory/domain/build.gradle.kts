plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.network)
    implementation(projects.storage)
    implementation(projects.common.data)
    implementation(projects.common.domain)
    implementation(projects.common.utils)
    implementation(projects.features.writestory.data)
    implementation(libs.ktor.serialization)
    implementation(libs.javax.inject)
}
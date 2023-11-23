plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.network)
    implementation(projects.common.data)
    implementation(libs.ktor.serialization)
    implementation(libs.javax.inject)
}
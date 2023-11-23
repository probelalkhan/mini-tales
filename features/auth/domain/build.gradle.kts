plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.features.auth.data)
    implementation(projects.common.data)
    implementation(projects.common.domain)
    implementation(projects.common.utils)
    implementation(projects.network)
    implementation(projects.storage)
    implementation(libs.javax.inject)
}
plugins {
    id("java-library")
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(projects.common.data)
    implementation(projects.common.domain)
    implementation(projects.common.utils)
    implementation(projects.features.home.data)
    implementation(projects.network)
    implementation(libs.javax.inject)
}
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MiniTales"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":app")
include(":network")
include(":features:auth")
include(":features:auth:data")
include(":features:auth:domain")
include(":storage")
include(":common")
include(":theme")
include(":features:home")
include(":features:home:data")
include(":features:profile")
include(":common:data")
include(":common:domain")
include(":common:utils")
include(":features:writestory")
include(":features:writestory:data")
include(":features:writestory:domain")
include(":features:home:domain")

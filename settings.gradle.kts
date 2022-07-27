pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven { url = uri("https://plugins.gradle.org/m2/") }
        google()
    }

    plugins {
        kotlin("jvm").version(extra["kotlin.version"] as String)
        kotlin("multiplatform").version(extra["kotlin.version"] as String)
        id("org.jetbrains.compose").version(extra["compose.version"] as String)
    }
}


include(":SplitPane:library")
include(":SplitPane:demo")
include(
    "dial-phone-api",
    "dial-phone-bot",
    "dial-phone-olm-machine"
)
//include(":SplitPane:trixnity-client")
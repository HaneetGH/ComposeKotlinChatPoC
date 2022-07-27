plugins {
    //kotlin("plugin.serialization")
    kotlin("multiplatform")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
    }
    js(org.jetbrains.kotlin.gradle.plugin.KotlinJsCompilerType.BOTH)

    val ktorVersion: String by rootProject.extra
    val kotlinxCoroutinesVersion: String by rootProject.extra
    val kotlinxSerializationVersion: String by rootProject.extra
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
                //Ktor
                implementation("io.ktor:ktor-client-core:1.6.4")
                implementation("io.ktor:ktor-client-content-negotiation:1.6.4")
                implementation("io.ktor:ktor-serialization-kotlinx-json:1.6.4")
                implementation("io.ktor:ktor-client-logging:1.6.4")
            }
        }
    }
}
import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("plugin.serialization") version "1.5.10"
    id("org.jetbrains.compose")
}
val trixnityVersion = "2.0.0"

fun trixnity(module: String, version: String = trixnityVersion) =
    "net.folivo:trixnity-$module:$version"
kotlin {
    jvm {}
    repositories {
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    sourceSets {
        named("jvmMain") {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":SplitPane:library"))
                implementation(project(":SplitPane:demo"))
                    //implementation(trixnity("trixnity-client"))
             implementation("io.github.dominaezzz.matrixkt:client:0.2.0")
                implementation("io.github.dominaezzz.matrixkt:olm:0.2.0")
                implementation("io.coil-kt:coil-compose:1.4.0")
                implementation("androidx.compose.runtime:runtime-livedata:1.1.0")
                implementation("io.ktor:ktor-client-core:1.6.4")
                implementation("io.ktor:ktor-client-cio:1.6.4")
                implementation("io.ktor:ktor-client-serialization:1.6.4")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
                implementation(trixnity("olm"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "org.jetbrains.compose.splitpane.demo.MainKt"
        nativeDistributions {
            targetFormats(
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Dmg,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Msi,
                org.jetbrains.compose.desktop.application.dsl.TargetFormat.Exe
            )
        }

    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
}
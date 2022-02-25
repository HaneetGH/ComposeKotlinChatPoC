#Compose For Desktop

Developing a sample compose app for Desktop dmg/exe

#Prerequisites

Compose for Desktop can produce applications for macOS, Linux and Windows platforms. So any of these platforms can be used for this tutorial.

The following software has to be preinstalled:

JDK 11 or later
IntelliJ IDEA Community Edition or Ultimate Edition 2020.3 or later (other editors could be used, but we assume you are using IntelliJ IDEA in this tutorial)

#IDE plugin

Compose Multiplatform IDEA plugin can simplify compose development by adding support for @Preview annotation on argument-less @Composable functions. One could see how particular composable function looks like directly in IDE panel. This plugin could also be discovered via plugins marketplace, just search for "Compose Multiplatform".

Update the wizard plugin

The Compose plugin version used in the wizard above may be not the last. Update the version of the plugin to the latest available by editing the build.gradle.kts file, finding and updating the version information as shown below. For the latest versions, see the latest versions site and the Kotlin site.
```
plugins {
kotlin("jvm") version "1.6.10"
id("org.jetbrains.compose") version "1.0.1-rc2"
}
```



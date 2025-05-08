pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://plugins.gradle.org/m2/")
    }
    
    plugins {
        val kotlinVersion = "1.9.21"
        val agpVersion = "8.2.0"
        val composeVersion = "1.5.11"
        
        kotlin("multiplatform") version kotlinVersion
        kotlin("android") version kotlinVersion
        kotlin("native.cocoapods") version kotlinVersion
        id("com.android.application") version agpVersion
        id("com.android.library") version agpVersion
        id("org.jetbrains.kotlin.plugin.serialization") version kotlinVersion
        id("org.jetbrains.compose") version composeVersion
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://plugins.gradle.org/m2/")
    }
}

rootProject.name = "CurrencyConverter"
include(":androidApp")
include(":shared")
include(":iosApp") 
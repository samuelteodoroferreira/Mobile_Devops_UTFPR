plugins {
    // Apply the org.jetbrains.kotlin.multiplatform plugin. Add the version in settings.gradle.kts
    kotlin("multiplatform").apply(false)
    // Apply the Android application plugin. Add the version in settings.gradle.kts
    id("com.android.application").apply(false)
    // Apply the Android library plugin. Add the version in settings.gradle.kts
    id("com.android.library").apply(false)
    // Apply the Kotlin Android plugin
    id("org.jetbrains.kotlin.android").apply(false)
    id("org.jetbrains.kotlin.plugin.serialization") apply false
    id("org.jetbrains.compose") version "1.4.3" apply false
}

tasks.register("clean", Delete::class) {
    delete(layout.buildDirectory)
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.20")
        classpath("com.android.tools.build:gradle:8.0.2")
        classpath("org.jetbrains.compose:compose-gradle-plugin:1.4.3")
    }
} 
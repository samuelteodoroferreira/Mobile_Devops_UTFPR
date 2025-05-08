plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
    id("org.jetbrains.compose")
}

kotlin {
    android()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("io.ktor:ktor-client-core:2.3.7")
                implementation("io.ktor:ktor-client-content-negotiation:2.3.7")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.7")
                implementation("io.insert-koin:koin-core:3.5.0")
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation("org.jetbrains.compose.components:components-resources:1.7.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation("io.mockk:mockk:1.13.8")
                implementation("io.insert-koin:koin-test:3.5.0")
                implementation("androidx.compose.ui:ui-test-junit4:1.8.0")
                implementation("org.jetbrains.compose.ui:ui-test-junit4:1.5.11")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:2.3.7")
                implementation("io.insert-koin:koin-android:3.5.0")
                implementation("androidx.compose.ui:ui:1.8.0")
                implementation("androidx.compose.ui:ui-tooling:1.8.0")
                implementation("androidx.compose.ui:ui-tooling-preview:1.8.0")
                implementation("androidx.compose.foundation:foundation:1.8.0")
                implementation("androidx.compose.material3:material3:1.3.2")
                implementation("androidx.activity:activity-compose:1.10.1")
            }
        }
    }
}

android {
    namespace = "com.currencyconverter.shared"
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
        viewBinding = false
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    buildToolsVersion = "30.0.3"
}
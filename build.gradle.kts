// Root build.gradle.kts
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.gradle.versions.plugin)
        classpath(libs.google.services)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.gms.google-services") version "4.4.3" apply false
    id("com.github.ben-manes.versions") version "0.52.0"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

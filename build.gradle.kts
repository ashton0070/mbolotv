// Projet racine Mbolo — application Android native (Kotlin + Jetpack Compose).
buildscript {
    dependencies {
        // AGP 9 embarque Kotlin 2.2.10 par défaut ; on impose Kotlin 2.4.20.
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

plugins {
    // Android Gradle Plugin version — DECLARE ICI
    id("com.android.application") version "8.6.1" apply false
    id("com.android.library") version "8.6.1" apply false

    // Kotlin (utilise la version 2.2.0 que tu as choisie)
    id("org.jetbrains.kotlin.multiplatform") version "2.2.0" apply false
    id("org.jetbrains.kotlin.jvm") version "2.2.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.0" apply false
    id("org.jetbrains.kotlin.kapt") version "2.2.0" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.2.0" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.2.0" apply false
    id("org.jetbrains.compose") version "1.8.2" apply false
    id("app.cash.sqldelight") version "2.0.2" apply false
}
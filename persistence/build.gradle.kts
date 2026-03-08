plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("app.cash.sqldelight")
}

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation("app.cash.sqldelight:runtime:2.0.2")
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.2")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:android-driver:2.0.2")
            }
        }
        val desktopMain by getting {
            dependencies {
                implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
            }
        }
    }
}

android {
    namespace = "com.example.pizzapp.persistence"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
    }
}

sqldelight {
    databases {
        create("PizzAppDatabase") {
            packageName.set("com.example.pizzapp.persistence.db")
        }
    }
}

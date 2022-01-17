import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    kotlin("multiplatform")
    id("kotlinx-serialization")
    id("com.android.library")
    id("com.codingfeline.buildkonfig")
}

// CocoaPods requires the podspec to have a version.
version = "1.0"

android {
    compileSdk = Versions.androidCompileSdk
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = Versions.androidMinSdk
        targetSdk = Versions.androidTargetSdk
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

// Workaround for https://youtrack.jetbrains.com/issue/KT-43944
android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}

buildkonfig {
    packageName = "com.mikepenz.common"
    defaultConfigs {
        buildConfigField(com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING, "STORYBLOK_TOKEN", Properties().also {
            if (rootProject.file("local.properties").exists()) {
                it.load(rootProject.file("local.properties").inputStream())
            }
        }.getProperty("storyblok.token", null) ?: project.property("storyblok.token")?.toString() ?: "")
    }
}

kotlin {
    android()
    jvm()

    sourceSets {
        sourceSets["commonMain"].dependencies {
            with(Deps.Kotlinx) {
                implementation(coroutinesCore)
                implementation(dateTime)
            }

            with(Deps.StoryblokSdk) {
                api(core)
            }

            with(Deps.Ktor) {
                implementation(clientCore)
                implementation(clientJson)
                implementation(clientLogging)
                implementation(clientSerialization)
            }

            with(Deps.Kotlinx) {
                implementation(serializationCore)
            }

            with(Deps.Koin) {
                api(core)
                api(test)
            }

            with(Deps.Log) {
                api(kermit)
            }
        }
        sourceSets["commonTest"].dependencies {
        }

        sourceSets["androidMain"].dependencies {
            implementation(Deps.Ktor.clientAndroid)
        }
        sourceSets["androidTest"].dependencies {
            implementation(Deps.Test.junit)
        }

        sourceSets["jvmMain"].dependencies {
            implementation(Deps.Ktor.clientJava)
            implementation(Deps.Log.slf4j)
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
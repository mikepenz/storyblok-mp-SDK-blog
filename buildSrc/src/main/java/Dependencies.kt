object Versions {
    const val androidMinSdk = 21
    const val androidCompileSdk = 31
    const val androidTargetSdk = androidCompileSdk

    const val kotlin = "1.6.10"

    const val kotlinCoroutines = "1.6.0"
    const val koin = "3.1.5"
    const val ktor = "2.0.0"
    const val kotlinxSerialization = "1.3.2"
    const val kotlinxHtmlJs = "0.7.3"
    const val kotlinxDateTime = "0.3.2"

    const val storyblok = "2.0.0-rc02"
    const val markdownRenderer = "0.4.0"
    const val aboutLibraries = "10.1.0"

    const val compose = "1.1.0-rc01"
    const val composeCompiler = "1.1.0-rc02"
    const val navCompose = "2.4.0-rc01"
    const val accompanist = "0.22.0-rc"

    const val junit = "4.13.2"
    const val androidXTestJUnit = "1.1.3"
    const val testCore = "1.4.0"
    const val mockito = "3.12.4"
    const val robolectric = "4.7.3"

    const val shadow = "7.0.0"
    const val kotlinterGradle = "3.8.0"
    const val buildkonfig = "0.11.0"

    const val material = "1.5.0"
    const val activityCompose = "1.4.0"
    const val lifecycleKtx = "2.4.0"
    const val lifecycleRuntimeKtx = lifecycleKtx
    const val lifecycleViewmodelKtx = lifecycleKtx

    const val slf4j = "1.7.33"
    const val kermit = "1.0.3"
}

object Deps {
    object Gradle {
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
        const val kotlinSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Versions.kotlin}"
        const val kotlinter = "org.jmailen.gradle:kotlinter-gradle:${Versions.kotlinterGradle}"
        const val shadow = "gradle.plugin.com.github.jengelman.gradle.plugins:shadow:${Versions.shadow}"
        const val buildkonfig = "com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:${Versions.buildkonfig}"
        const val aboutLibraries = "com.mikepenz.aboutlibraries.plugin:aboutlibraries-plugin:${Versions.aboutLibraries}"
    }

    object Kotlinx {
        const val serializationCore = "org.jetbrains.kotlinx:kotlinx-serialization-core:${Versions.kotlinxSerialization}"
        const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.kotlinxSerialization}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlinCoroutines}"
        const val dateTime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDateTime}"
    }

    object Android {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object AndroidX {
        const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtx}"
        const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycleViewmodelKtx}"
        const val activityCompose = "androidx.activity:activity-compose:${Versions.activityCompose}"
    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
        const val androidXTestJUnit = "androidx.test.ext:junit:${Versions.androidXTestJUnit}"
        const val mockito = "org.mockito:mockito-inline:${Versions.mockito}"
        const val robolectric = "org.robolectric:robolectric:${Versions.robolectric}"
        const val testCore = "androidx.test:core:${Versions.testCore}"

        const val composeUiTest = "androidx.compose.ui:ui-test:${Versions.compose}"
        const val composeUiTestJUnit = "androidx.compose.ui:ui-test-junit4:${Versions.compose}"
        const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose}"
    }

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val uiGraphics = "androidx.compose.ui:ui-graphics:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.navCompose}"

        const val coilCompose = "io.coil-kt:coil-compose:1.4.0"
        const val accompanistInsets = "com.google.accompanist:accompanist-insets:${Versions.accompanist}"
        const val accompanistInsetsUi = "com.google.accompanist:accompanist-insets-ui:${Versions.accompanist}"
        const val accompanistNavigationAnimation = "com.google.accompanist:accompanist-navigation-animation:${Versions.accompanist}"

        const val aboutLibraries = "com.mikepenz:aboutlibraries-compose:${Versions.aboutLibraries}"
    }

    object StoryblokSdk {
        const val core = "com.mikepenz:storyblok-mp-sdk:${Versions.storyblok}"
    }

    object MarkdownRenderer {
        const val core = "com.mikepenz:multiplatform-markdown-renderer:${Versions.markdownRenderer}"
    }

    object Koin {
        const val core = "io.insert-koin:koin-core:${Versions.koin}"
        const val test = "io.insert-koin:koin-test:${Versions.koin}"
        const val testJUnit4 = "io.insert-koin:koin-test-junit4:${Versions.koin}"
        const val android = "io.insert-koin:koin-android:${Versions.koin}"
        const val compose = "io.insert-koin:koin-androidx-compose:${Versions.koin}"
    }

    object Ktor {
        const val serialization = "io.ktor:ktor-serialization:${Versions.ktor}"
        const val clientCore = "io.ktor:ktor-client-core:${Versions.ktor}"
        const val clientJson = "io.ktor:ktor-client-content-negotiation:${Versions.ktor}"
        const val clientLogging = "io.ktor:ktor-client-logging:${Versions.ktor}"
        const val clientSerialization = "io.ktor:ktor-serialization-kotlinx-json:${Versions.ktor}"
        const val clientAndroid = "io.ktor:ktor-client-android:${Versions.ktor}"
        const val clientJava = "io.ktor:ktor-client-java:${Versions.ktor}"
    }

    object Log {
        const val slf4j = "org.slf4j:slf4j-simple:${Versions.slf4j}"
        const val kermit = "co.touchlab:kermit:${Versions.kermit}"
    }
}

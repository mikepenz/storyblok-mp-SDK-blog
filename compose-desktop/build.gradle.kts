import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose") version "1.1.1"
    id("com.mikepenz.aboutlibraries.plugin")
    application
}

group = "com.mikepenz"
version = "0.1.0"

repositories {
    mavenCentral()
    maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(compose.foundation)
    implementation(compose.material)
    implementation(project(":common"))

    with(Deps.MarkdownRenderer) {
        implementation(core)
    }

    with(Deps.Kotlinx) {
        implementation(coroutinesCore)
        implementation(serializationCore)
        implementation(serializationJson)
        implementation(dateTime)
    }

    with(Deps.Compose) {
        implementation(aboutLibraries)
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}

aboutLibraries {
    registerAndroidTasks = false
}
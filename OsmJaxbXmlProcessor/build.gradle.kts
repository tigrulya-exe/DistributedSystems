plugins {
    kotlin("jvm")
}

group = "ru.nsu.manasyan"
version = "1.0"

repositories {
    mavenCentral()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":osm-processor-lib"))
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "ru.nsu.manasyan.osm.MainKt"
    }
    configurations["compileClasspath"].forEach {
        from(zipTree(it.absoluteFile))
    }
}
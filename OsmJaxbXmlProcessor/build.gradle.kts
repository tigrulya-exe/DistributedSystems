plugins {
    kotlin("jvm")
    id("com.github.jacobono.jaxb") version "1.3.5"
}

group = "ru.nsu.manasyan"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(project(":osm-processor-lib"))

    implementation("com.sun.xml.bind:jaxb-xjc:3.0.0")
    implementation("com.sun.xml.bind:jaxb-impl:2.3.1")
    implementation("com.sun.xml.bind:jaxb-core:2.3.0.1")
    implementation("javax.xml.bind:jaxb-api:2.3.1")

    jaxb("javax.activation:activation:1.1.1")
    jaxb("com.sun.xml.bind:jaxb-xjc:3.0.0")
    jaxb("com.sun.xml.bind:jaxb-impl:3.0.0")
    jaxb("javax.xml.bind:jaxb-api:2.2.7")
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "ru.nsu.manasyan.osm.MainKt"
        }
        configurations["compileClasspath"].forEach {
            from(zipTree(it.absoluteFile))
        }
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }

    xjc {
        jaxb {
            xjc {
                generatePackage = "ru.nsu.manasyan.osm.model.generated"
            }
            xsdDir = "src/main/resources/"
        }
    }
}

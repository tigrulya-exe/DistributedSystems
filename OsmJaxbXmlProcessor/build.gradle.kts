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

    val sunJaxbVersion = "2.2.7-b41"
    val apiJaxbVersion = "2.2.7"
    implementation("com.sun.xml.bind:jaxb-xjc:$sunJaxbVersion")
    implementation("com.sun.xml.bind:jaxb-impl:$sunJaxbVersion")
//    implementation("com.sun.xml.bind:jaxb-core:$sunJaxbVersion")
    implementation("javax.xml.bind:jaxb-api:$apiJaxbVersion")
    implementation("javax.activation:activation:1.1.1")

    jaxb("javax.activation:activation:1.1.1")
    jaxb("com.sun.xml.bind:jaxb-xjc:$sunJaxbVersion")
    jaxb("com.sun.xml.bind:jaxb-impl:$sunJaxbVersion")
    jaxb("javax.xml.bind:jaxb-api:$apiJaxbVersion")
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

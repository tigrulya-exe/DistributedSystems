plugins {
    kotlin("jvm") version "1.4.10"
    id("com.github.jacobono.jaxb") version "1.3.5"
}

group = "ru.nsu.manasyan"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.commons:commons-compress:1.3")

    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.12.1")

    val sunJaxbVersion = "2.2.7-b41"
    val apiJaxbVersion = "2.2.7"
    implementation("com.sun.xml.bind:jaxb-xjc:$sunJaxbVersion")
    implementation("com.sun.xml.bind:jaxb-impl:$sunJaxbVersion")
//    implementation("com.sun.xml.bind:jaxb-core:$sunJaxbVersion")
    implementation("javax.xml.bind:jaxb-api:$apiJaxbVersion")
    implementation("javax.activation:activation:1.1.1")

    implementation("org.postgresql:postgresql:42.2.18")
    implementation("com.zaxxer:HikariCP:4.0.3")

    val log4jVersion = "2.14.0"
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:$log4jVersion")

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

    jaxb {
        xjc {
            generatePackage = "ru.nsu.manasyan.osm.model.generated"
        }
        xsdDir = "src/main/resources/"
    }
}

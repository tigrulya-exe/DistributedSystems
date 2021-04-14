plugins {
    kotlin("jvm") version "1.4.32"
    kotlin("plugin.spring") version "1.4.31"
    kotlin("plugin.jpa") version "1.3.21"

    id("org.flywaydb.flyway") version "6.3.0"
    id("com.github.jacobono.jaxb") version "1.3.5"
    id("org.springframework.boot") version "2.4.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "ru.nsu.manasyan"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.commons:commons-compress:1.3")

    val sunJaxbVersion = "2.2.7-b41"
    val apiJaxbVersion = "2.2.7"
    implementation("com.sun.xml.bind:jaxb-xjc:$sunJaxbVersion")
    implementation("com.sun.xml.bind:jaxb-impl:$sunJaxbVersion")
    implementation("javax.xml.bind:jaxb-api:$apiJaxbVersion")
    implementation("javax.activation:activation:1.1.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("org.postgresql:postgresql:42.2.14")

    jaxb("javax.activation:activation:1.1.1")
    jaxb("com.sun.xml.bind:jaxb-xjc:$sunJaxbVersion")
    jaxb("com.sun.xml.bind:jaxb-impl:$sunJaxbVersion")
    jaxb("javax.xml.bind:jaxb-api:$apiJaxbVersion")
}

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    test {
        useJUnitPlatform()
    }

    flyway {
        url = "jdbc:postgresql://localhost:5432/Osm"
        user = "postgres"
        password = "password"
        baselineOnMigrate = true
    }

    jaxb {
        xjc {
            generatePackage = "ru.nsu.manasyan.osm.model.generated"
        }
        xsdDir = "src/main/resources/"
    }
}
rootProject.name = "OsmJaxbXmlProcessor"
include(":osm-processor-lib")

project(":osm-processor-lib").projectDir = File("../OsmXmlProcessor/OsmXmlProcessor")

pluginManagement {
    plugins {
        kotlin("jvm") version "1.4.10"
    }
}
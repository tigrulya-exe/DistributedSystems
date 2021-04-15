package ru.nsu.manasyan.osm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import ru.nsu.manasyan.osm.properties.OsmDbInitProperties

@SpringBootApplication
@EnableConfigurationProperties(OsmDbInitProperties::class)
class OsmApplication {
    companion object {
        const val API_PREFIX = "/api"
    }
}

fun main(args: Array<String>) {
    runApplication<OsmApplication>(*args)
}
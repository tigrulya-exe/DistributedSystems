package ru.nsu.manasyan.osm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class OsmApplication {
    companion object {
        const val API_PREFIX = "/api/"
    }
}

fun main(args: Array<String>) {
    runApplication<OsmApplication>(*args)
}
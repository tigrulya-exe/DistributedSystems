package ru.nsu.manasyan.osm.properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

object DbPropertiesParser {
    private val mapper = ObjectMapper(YAMLFactory())

    fun parse(propertiesPath: String): DbProperties = mapper.readValue(
        this.javaClass
            .classLoader
            .getResourceAsStream(propertiesPath),
        DbProperties::class.java
    )
}
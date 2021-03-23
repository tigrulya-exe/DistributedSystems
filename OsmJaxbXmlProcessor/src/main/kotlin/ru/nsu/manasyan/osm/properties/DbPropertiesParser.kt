package ru.nsu.manasyan.osm.properties

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory

object DbPropertiesParser {
    private const val DEFAULT_PROPERTIES_PATH = "application.yaml"

    private val mapper = ObjectMapper(YAMLFactory())

    fun parse(
        propertiesPath: String = DEFAULT_PROPERTIES_PATH
    ): DbProperties = mapper.readValue(
        this.javaClass
            .classLoader
            .getResourceAsStream(propertiesPath),
        DbProperties::class.java
    )
}
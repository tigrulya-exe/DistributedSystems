package ru.nsu.manasyan.osm.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("osm.db.init")
class OsmDbInitProperties(
    val filePath: String = "osm.bz2",
    val enabled: Boolean = false
)
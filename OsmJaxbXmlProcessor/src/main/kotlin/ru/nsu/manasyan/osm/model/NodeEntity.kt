package ru.nsu.manasyan.osm.model

import java.math.BigInteger

class NodeEntity {
    lateinit var id: BigInteger

    lateinit var user: String

    var latitude: Double = 0.0

    var longitude: Double = 0.0

    lateinit var tags: List<TagEntity>
}

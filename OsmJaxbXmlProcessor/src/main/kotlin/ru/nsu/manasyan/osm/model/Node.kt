package ru.nsu.manasyan.osm.model

import java.math.BigInteger
import java.time.LocalDateTime

class Node {
    var id: BigInteger? = null

    var user: String? = null

    var uid: BigInteger? = null

    var lat: Double? = null

    var lon: Double? = null

    var version: BigInteger? = null

    var changeset: BigInteger? = null

    var timestamp: LocalDateTime? = null

    var tags: List<Tag>? = null
}

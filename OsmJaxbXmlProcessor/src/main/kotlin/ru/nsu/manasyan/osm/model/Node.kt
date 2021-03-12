package ru.nsu.manasyan.osm.model

import java.math.BigInteger
import java.time.LocalDateTime

class Node {
    lateinit var id: BigInteger

    lateinit var user: String

    lateinit var uid: BigInteger

    var lat: Double = 0.0

    var lon: Double = 0.0

    lateinit var version: BigInteger

    lateinit var changeset: BigInteger

    lateinit var timestamp: LocalDateTime

    lateinit var tags: List<Tag>
}

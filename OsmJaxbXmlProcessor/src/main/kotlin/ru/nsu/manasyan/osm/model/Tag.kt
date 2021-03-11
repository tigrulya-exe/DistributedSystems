package ru.nsu.manasyan.osm.model

import java.math.BigInteger

class Tag(
    var key: String? = null,
    var value: String? = null,
    var nodeId: BigInteger? = null
)
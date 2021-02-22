package ru.nsu.manasyan.osm

class Statistics {
    val value: MutableMap<String, Int> = hashMapOf()

    fun increment(key: String) = value.merge(key, 1, Int::plus)
}

class OsmStatistics(
    val editsByUser: Statistics = Statistics(),
    val nodesCountByTag: Statistics = Statistics()
)
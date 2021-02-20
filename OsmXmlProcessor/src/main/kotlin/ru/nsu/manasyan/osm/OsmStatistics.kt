package ru.nsu.manasyan.osm

class Statistics {
    private val value: MutableMap<String, Int> = hashMapOf()
    fun increment(key: String) = value.merge(key, 1, Int::plus)
    fun print() {
        value.entries
            .sortedByDescending { it.value }
            .forEach {
                println("${it.key}: ${it.value}")
            }
    }
}

class OsmStatistics(
    val editsByUser: Statistics = Statistics(),
    val nodesCountByTag: Statistics = Statistics(),
)
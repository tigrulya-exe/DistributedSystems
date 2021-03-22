package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.model.Node

interface SingleConnectionNodeService : AutoCloseable {
    fun save(entity: Node)
}
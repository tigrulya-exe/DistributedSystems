package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.model.NodeEntity

interface SingleConnectionNodeService : AutoCloseable {
    fun save(entity: NodeEntity)
}
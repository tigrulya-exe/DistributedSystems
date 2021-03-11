package ru.nsu.manasyan.osm.db.dao

import ru.nsu.manasyan.osm.model.Node
import ru.nsu.manasyan.osm.model.Tag

interface OsmDao {
    fun saveNode(node: Node)

    fun saveTag(tag: Tag)
}
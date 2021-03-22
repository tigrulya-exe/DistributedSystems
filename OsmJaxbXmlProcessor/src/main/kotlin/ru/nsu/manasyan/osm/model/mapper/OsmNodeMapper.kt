package ru.nsu.manasyan.osm.model.mapper

import ru.nsu.manasyan.osm.model.Node as DbNode
import ru.nsu.manasyan.osm.model.Tag as DbTag
import ru.nsu.manasyan.osm.model.generated.Node as GeneratedNode

object OsmNodeMapper {
    fun toDbNode(node: GeneratedNode): DbNode =
        DbNode().apply {
            this.id = node.id
            this.latitude = node.lat
            this.longitude = node.lon
            this.user = node.user
            this.tags = node.tag.map {
                DbTag(
                    key = it.k,
                    value = it.v,
                    nodeId = node.id
                )
            }
        }
}
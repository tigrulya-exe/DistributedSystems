package ru.nsu.manasyan.osm.model.mapper

import ru.nsu.manasyan.osm.model.Node as DbNode
import ru.nsu.manasyan.osm.model.Tag as DbTag
import ru.nsu.manasyan.osm.model.generated.Node as GeneratedNode

object OsmNodeMapper {
    fun toDbNode(node: GeneratedNode): DbNode =
        DbNode().apply {
            this.id = node.id
            this.changeset = node.changeset
            this.lat = node.lat
            this.lon = node.lon
            this.uid = node.uid
            this.user = node.user
            this.version = node.version
            this.timestamp = node.timestamp
                .toGregorianCalendar()
                .toZonedDateTime()
                .toLocalDateTime()
            this.tags = node.tag.map {
                DbTag(
                    key = it.k,
                    value = it.v,
                    nodeId = node.id
                )
            }
        }
}
package ru.nsu.manasyan.osm.model.mapper

import org.springframework.stereotype.Component
import ru.nsu.manasyan.osm.model.entity.NodeEntity
import ru.nsu.manasyan.osm.model.entity.TagEntity
import ru.nsu.manasyan.osm.model.generated.Node

@Component
class XmlToDbNodeMapper : Mapper<Node, NodeEntity> {
    override fun mapReversed(entity: NodeEntity) = TODO("No need")

    override fun map(entity: Node) = NodeEntity(
        id = entity.id.toLong(),
        user = entity.user,
        latitude = entity.lat,
        longitude = entity.lon
    ).apply {
        tags = entity.tag.map {
            TagEntity(
                key = it.k,
                value = it.v,
                node = this
            )
        }
    }
}
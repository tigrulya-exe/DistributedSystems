package ru.nsu.manasyan.osm.model.mapper

import org.springframework.stereotype.Component
import ru.nsu.manasyan.osm.model.dto.NodeDto
import ru.nsu.manasyan.osm.model.entity.NodeEntity
import ru.nsu.manasyan.osm.service.TagService

@Component
// TODO: TMP
class NodeToDtoMapper(
    private val tagService: TagService,
    private val tagMapper: TagToDtoMapper
) : Mapper<NodeEntity, NodeDto> {
    override fun mapReversed(entity: NodeDto): NodeEntity = NodeEntity(
        id = entity.id,
        user = entity.user,
        latitude = entity.latitude,
        longitude = entity.longitude
    ).apply {
        tags = entity.tags.map { tagService.get(it.id) }
    }

    override fun map(entity: NodeEntity): NodeDto = NodeDto(
        id = entity.id!!,
        user = entity.user,
        latitude = entity.latitude,
        longitude = entity.longitude,
        tags = tagMapper.mapIterable(entity.tags).toList()
    )
}
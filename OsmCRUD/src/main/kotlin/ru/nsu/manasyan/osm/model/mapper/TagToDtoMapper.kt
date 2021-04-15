package ru.nsu.manasyan.osm.model.mapper

import org.springframework.stereotype.Component
import ru.nsu.manasyan.osm.model.dto.TagDto
import ru.nsu.manasyan.osm.model.entity.TagEntity
import ru.nsu.manasyan.osm.service.NodeService

@Component
class TagToDtoMapper(
    private val nodeService: NodeService
) : Mapper<TagEntity, TagDto> {
    override fun map(entity: TagEntity) = TagDto(
        id = entity.id!!.toLong(),
        key = entity.key,
        value = entity.value,
        nodeId = entity.node.id!!
    )

    override fun mapReversed(entity: TagDto) = TagEntity(
        id = entity.id,
        key = entity.key,
        value = entity.value,
        node = nodeService.get(entity.nodeId)
    )
}
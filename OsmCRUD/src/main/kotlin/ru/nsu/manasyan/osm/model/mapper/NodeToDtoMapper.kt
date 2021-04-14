package ru.nsu.manasyan.osm.model.mapper

import org.springframework.stereotype.Component
import ru.nsu.manasyan.osm.model.dto.NodeDto
import ru.nsu.manasyan.osm.model.dto.TagDto
import ru.nsu.manasyan.osm.model.entity.NodeEntity
import ru.nsu.manasyan.osm.service.TagService

@Component
// TODO: TMP
class NodeToDtoMapper(
    private val tagService: TagService
) : Mapper<NodeEntity, NodeDto> {
    override fun toEntity(dto: NodeDto): NodeEntity = NodeEntity(
        user = dto.user,
        latitude = dto.latitude,
        longitude = dto.longitude
    ).apply {
        id = dto.id
        tags = dto.tags.map { tagService.get(it.id) }
    }

    override fun toDto(entity: NodeEntity): NodeDto = NodeDto(
        id = entity.id!!,
        user = entity.user,
        latitude = entity.latitude,
        longitude = entity.longitude,
        tags = entity.tags.map { TagDto(it.id!!, it.key, it.value) }
    )
}
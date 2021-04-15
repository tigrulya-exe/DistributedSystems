package ru.nsu.manasyan.osm.controller

import ru.nsu.manasyan.osm.model.dto.TagDto
import ru.nsu.manasyan.osm.model.entity.TagEntity
import ru.nsu.manasyan.osm.model.mapper.TagToDtoMapper
import ru.nsu.manasyan.osm.service.TagService

class TagCrudController(
    service: TagService,
    mapper: TagToDtoMapper
) : AbstractCrudController<TagEntity, TagDto>(service, mapper)
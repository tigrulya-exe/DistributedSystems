package ru.nsu.manasyan.osm.controller

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.nsu.manasyan.osm.OsmApplication.Companion.API_PREFIX
import ru.nsu.manasyan.osm.model.dto.NodeDto
import ru.nsu.manasyan.osm.model.entity.NodeEntity
import ru.nsu.manasyan.osm.model.mapper.NodeToDtoMapper
import ru.nsu.manasyan.osm.service.NodeService

@RestController
@RequestMapping("$API_PREFIX/nodes")
class NodeController(
    service: NodeService,
    mapper: NodeToDtoMapper
) : AbstractCrudController<NodeEntity, NodeDto>(service, mapper)
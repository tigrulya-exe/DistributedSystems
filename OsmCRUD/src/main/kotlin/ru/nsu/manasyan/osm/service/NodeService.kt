package ru.nsu.manasyan.osm.service

import org.springframework.stereotype.Service
import ru.nsu.manasyan.osm.model.entity.NodeEntity
import ru.nsu.manasyan.osm.repository.NodeRepository


@Service
class NodeService(
    override val repository: NodeRepository,
) : AbstractCrudService<NodeEntity>(repository) {
    override val entityName: String = "Node"

    fun getNodesNearby(
        longitude: Double,
        latitude: Double,
        radius: Double
    ): Iterable<NodeEntity> {
        return repository.getNodesNearby(
            longitude,
            latitude,
            radius
        )
    }

}
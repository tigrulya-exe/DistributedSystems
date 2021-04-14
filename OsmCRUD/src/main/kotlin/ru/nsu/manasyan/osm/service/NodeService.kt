package ru.nsu.manasyan.osm.service

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import ru.nsu.manasyan.osm.model.entity.NodeEntity

@Service
class NodeService(
    repository: JpaRepository<NodeEntity, Long>
) : AbstractCrudService<NodeEntity>(repository) {
    override val entityName: String = "Node"

}
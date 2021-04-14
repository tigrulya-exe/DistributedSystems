package ru.nsu.manasyan.osm.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import ru.nsu.manasyan.osm.model.entity.NodeEntity

@Repository
interface NodeRepository : JpaRepository<NodeEntity, Long>
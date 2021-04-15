package ru.nsu.manasyan.osm.processor

import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import ru.nsu.manasyan.osm.model.entity.NodeEntity
import ru.nsu.manasyan.osm.model.generated.Node
import ru.nsu.manasyan.osm.model.mapper.Mapper
import ru.nsu.manasyan.osm.properties.OsmDbInitProperties
import ru.nsu.manasyan.osm.service.NodeService
import ru.nsu.manasyan.osm.util.LoggerProperty


@Component
class DbInitializer(
    private val dbInitProperties: OsmDbInitProperties,
    private val nodeService: NodeService,
    private val mapper: Mapper<Node, NodeEntity>
) {
    companion object {
        private const val NODE_TAG = "node"
    }

    private val logger by LoggerProperty()

    private val processor = XmlProcessor()

    @EventListener(ContextRefreshedEvent::class)
    fun initializeDbIfNecessary() {
        if (dbInitProperties.enabled) {
            logger.info("Starting db initialization with script ${dbInitProperties.filePath}")
            processor.process<Node>(dbInitProperties.filePath, NODE_TAG) {
                nodeService.add(
                    mapper.map(it)
                )
            }
            logger.info("Db was successfully initialized")
        }
    }
}
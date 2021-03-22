package ru.nsu.manasyan.osm.processor

import ru.nsu.manasyan.osm.model.generated.Node
import ru.nsu.manasyan.osm.model.mapper.OsmNodeMapper
import ru.nsu.manasyan.osm.service.SingleConnectionNodeService

object OsmXmlProcessor {
    private const val NODE_TAG = "node"
    private val processor = XmlProcessor()

    fun process(inputFileName: String, nodeService: SingleConnectionNodeService) {
        nodeService.use { service ->
            processor.process<Node>(inputFileName, NODE_TAG) {
                service.save(
                    OsmNodeMapper.toDbNode(it)
                )
            }
        }
    }
}
package ru.nsu.manasyan.osm.processor

import ru.nsu.manasyan.osm.model.entity.NodeEntity
import ru.nsu.manasyan.osm.model.generated.Node

interface MockService {
    fun save(dbNode: NodeEntity)
}

object OsmXmlProcessor {
    private const val NODE_TAG = "node"
    private val processor = XmlProcessor()

    fun process(inputFileName: String, nodeService: MockService) {
        processor.process<Node>(inputFileName, NODE_TAG) {
//            nodeService.save(
//                OsmNodeMapper.toDbNode(it)
//            )
        }
    }
}
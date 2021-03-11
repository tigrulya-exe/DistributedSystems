package ru.nsu.manasyan.osm.processor

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.model.generated.Node
import ru.nsu.manasyan.osm.model.mapper.OsmNodeMapper

object OsmXmlProcessor {
    private const val NODE_TAG = "node"
    private val processor = XmlProcessor()

    fun process(inputFileName: String, dao: OsmDao) {
        processor.process<Node>(
            inputFileName,
            NODE_TAG
        ) { dao.saveNode(OsmNodeMapper.toDbNode(it)) }
    }
}
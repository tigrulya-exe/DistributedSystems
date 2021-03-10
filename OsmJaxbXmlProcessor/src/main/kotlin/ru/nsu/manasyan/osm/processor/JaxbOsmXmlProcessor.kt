package ru.nsu.manasyan.osm.processor

import ru.nsu.manasyan.osm.OsmStatistics
import ru.nsu.manasyan.osm.model.generated.Node
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.XMLStreamConstants
import javax.xml.stream.XMLStreamReader


class JaxbOsmXmlProcessor : OsmXmlProcessor() {
    private val unmarshaller: Unmarshaller

    private companion object {
        const val NODE_NAME = "node"
    }

    init {
        val context = JAXBContext.newInstance(Node::class.java)
        unmarshaller = context.createUnmarshaller()
    }

    override fun processDecompressedStream(stream: InputStream): OsmStatistics {
        val stats = OsmStatistics()
        val xmlFactory = XMLInputFactory.newInstance()
        var reader: XMLStreamReader? = null

        try {
            reader = xmlFactory.createXMLStreamReader(
                stream,
                StandardCharsets.UTF_8.name()
            )

            while (reader.hasNext()) {
                val event = reader.next()
                if (event == XMLStreamConstants.START_ELEMENT && reader.localName == NODE_NAME) {
                    handleNode(reader, stats)
                }
            }
        } finally {
            reader?.close()
        }

        return stats
    }

    private fun handleNode(
        reader: XMLStreamReader,
        osmStatistics: OsmStatistics
    ) {
        val node = unmarshaller.unmarshal(reader, Node::class.java).value
        osmStatistics.editsByUser.increment(node.user)
        node.tag.forEach {
            osmStatistics.nodesCountByTag.increment(it.k)
        }
    }
}

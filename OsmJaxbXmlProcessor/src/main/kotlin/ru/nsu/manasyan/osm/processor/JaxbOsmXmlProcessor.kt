package ru.nsu.manasyan.osm.processor

import ru.nsu.manasyan.osm.OsmStatistics
import ru.nsu.manasyan.osm.model.generated.Node
import ru.nsu.manasyan.osm.model.generated.Tag
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.xml.bind.JAXBContext
import javax.xml.bind.Unmarshaller
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.events.StartElement


class JaxbOsmXmlProcessor : OsmXmlProcessor() {
    private val unmarshaller: Unmarshaller

    private companion object {
        const val NODE_NAME = "node"
    }

    init {
        val context = JAXBContext.newInstance(Node::class.java, Tag::class.java)
        unmarshaller = context.createUnmarshaller()
    }

    override fun processDecompressedStream(stream: InputStream): OsmStatistics {
        val stats = OsmStatistics()
        val xmlFactory = XMLInputFactory.newInstance()
        var filteredReader: XMLEventReader? = null

        try {
            filteredReader = xmlFactory.createXMLEventReader(
                    stream,
                    StandardCharsets.UTF_8.name()
                )

            while (filteredReader.hasNext()) {
                val element = filteredReader.peek()
                if ((element is StartElement) && element.name.localPart == NODE_NAME) {
                    handleNode(filteredReader, stats)
                    continue
                }
                filteredReader?.nextEvent()
            }
        } finally {
            filteredReader?.close()
        }

        return stats
    }

    private fun handleNode(
        reader: XMLEventReader,
        osmStatistics: OsmStatistics
    ) {
        val node: Node = unmarshaller.unmarshal(reader, Node::class.java).value
        osmStatistics.editsByUser.increment(node.user)
        node.tag.forEach {
            osmStatistics.nodesCountByTag.increment(it.k)
        }
    }
}

//private class XsiTypeReader(reader: XMLEventReader?) : EventReaderDelegate(reader) {
//    override fun getAttributeNamespace(arg0: Int): String {
//        val element = filteredReader.peek()
//        return if ((element is StartElement) && element. ) {
//        "http://www.w3.org/2001/XMLSchema-instance"
//        } else super.getAttributeNamespace(arg0)
//    }
//}

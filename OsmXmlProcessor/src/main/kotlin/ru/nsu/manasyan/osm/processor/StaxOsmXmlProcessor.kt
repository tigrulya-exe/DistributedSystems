package ru.nsu.manasyan.osm.processor

import ru.nsu.manasyan.osm.model.OsmStatistics
import ru.nsu.manasyan.osm.model.Statistics
import java.io.InputStream
import java.nio.charset.StandardCharsets
import javax.xml.namespace.QName
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.events.StartElement

class StaxOsmXmlProcessor : OsmXmlProcessor() {
    private companion object {
        const val USER_ATTRIBUTE = "user"
        const val KEY_ATTRIBUTE = "k"
        const val NODE_NAME = "node"
        const val TAG_NAME = "tag"
    }

    override fun processDecompressedStream(stream: InputStream): OsmStatistics {
        val stats = OsmStatistics()

        val xmlFactory = XMLInputFactory.newInstance()
        val filteredReader = xmlFactory.createFilteredReader(
            xmlFactory.createXMLEventReader(
                stream,
                StandardCharsets.UTF_8.name()
            )
        ) {
            it is StartElement
        }
        try {
            while (filteredReader.hasNext()) {
                val element = filteredReader.nextEvent() as StartElement
                when (element.name.localPart) {
                    NODE_NAME -> incrementStatistics(element, USER_ATTRIBUTE, stats.editsByUser)
                    TAG_NAME -> incrementStatistics(element, KEY_ATTRIBUTE, stats.nodesCountByTag)
                }
            }
        } finally {
            filteredReader.close()
        }

        return stats
    }

    private fun incrementStatistics(
        element: StartElement,
        attributeName: String,
        statistics: Statistics
    ) {
        val key = element.getAttributeByName(
            QName.valueOf(attributeName)
        )?.value ?: return
        statistics.increment(key)
    }

}
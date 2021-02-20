package ru.nsu.manasyan.osm

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import java.io.BufferedInputStream
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.namespace.QName
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.events.StartElement

class OsmXmlProcessor {
    private companion object {
        const val USER_ATTRIBUTE = "user"
        const val KEY_ATTRIBUTE = "k"
        const val NODE_NAME = "node"
        const val TAG_NAME = "tag"
    }

    fun process(fileName: String): OsmStatistics {
        val stats = OsmStatistics()

        getDecompressedBZ2Stream(fileName).use {
            val xmlFactory = XMLInputFactory.newInstance()
            val filteredReader = xmlFactory.createFilteredReader(
                xmlFactory.createXMLEventReader(it)
            ) { it is StartElement }
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
        }
        return stats
    }

    private fun getDecompressedBZ2Stream(fileName: String) =
        BZip2CompressorInputStream(
            BufferedInputStream(
                Files.newInputStream(
                    Path.of(fileName),
                )
            )
        )

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
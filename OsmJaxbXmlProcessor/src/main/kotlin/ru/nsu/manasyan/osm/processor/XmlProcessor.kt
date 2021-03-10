package ru.nsu.manasyan.osm.processor

import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream
import java.io.BufferedInputStream
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Path
import java.util.function.Consumer
import javax.xml.bind.JAXBContext
import javax.xml.stream.XMLEventReader
import javax.xml.stream.XMLInputFactory
import javax.xml.stream.events.StartElement

typealias NodeHandler<T> = Consumer<T>

object XmlProcessor {
    inline fun <reified T> process(
        fileName: String,
        nodeName: String,
        handler: NodeHandler<T>
    ) {
        return BZip2CompressorInputStream(
            BufferedInputStream(
                Files.newInputStream(
                    Path.of(fileName),
                )
            )
        ).use {
            processDecompressedStream(it, nodeName, handler)
        }
    }

    inline fun <reified T> processDecompressedStream(
        stream: InputStream,
        nodeName: String,
        handler: NodeHandler<T>
    ) {
        val unmarshaller = JAXBContext
            .newInstance(T::class.java)
            .createUnmarshaller()

        var reader: XMLEventReader? = null
        try {
            reader = XMLInputFactory
                .newInstance()
                .createXMLEventReader(stream)

            while (reader.hasNext()) {
                val event = reader.peek()
                if (event is StartElement && event.name.localPart == nodeName) {
                    val node = unmarshaller.unmarshal(reader, T::class.java).value
                    handler.accept(node)
                    continue
                }
                reader.next()
            }
        } finally {
            reader?.close()
        }
    }
}
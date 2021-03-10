package ru.nsu.manasyan.osm

import ru.nsu.manasyan.osm.model.generated.Node
import ru.nsu.manasyan.osm.processor.XmlProcessor
import java.nio.file.NoSuchFileException
import javax.xml.stream.XMLStreamException

fun main(args: Array<String>) {
    try {
        val nodes = mutableSetOf<Node>()
        XmlProcessor.process(
            ArgsResolver.getInputFilePath(args),
            "node"
        ) { n: Node -> nodes.add(n) }
        println(nodes.size)
    } catch (exc: WrongArgumentException) {
//        log.error(exc.localizedMessage)
        println(ArgsResolver.usage())
    } catch (exc: XMLStreamException) {
//        log.error("Error reading from XML")
    } catch (exc: NoSuchFileException) {
//        log.error("No file was found with the provided path")
    }
}

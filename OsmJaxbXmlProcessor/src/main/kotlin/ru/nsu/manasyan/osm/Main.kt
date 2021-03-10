package ru.nsu.manasyan.osm

import ru.nsu.manasyan.osm.processor.JaxbOsmXmlProcessor
import java.nio.file.NoSuchFileException
import javax.xml.stream.XMLStreamException

fun main(args: Array<String>) {
    try {
        val result = JaxbOsmXmlProcessor().process(
            ArgsResolver.getInputFilePath(args)
        )
        println("User edits: ")
        printStats(result.editsByUser)
        println("Node counts by tag: ")
        printStats(result.nodesCountByTag)
    } catch (exc: WrongArgumentException) {
//        log.error(exc.localizedMessage)
        println(ArgsResolver.usage())
    } catch (exc: XMLStreamException) {
//        log.error("Error reading from XML")
    } catch (exc: NoSuchFileException) {
//        log.error("No file was found with the provided path")
    }
}

fun printStats(stats: Statistics) {
    stats.value
        .entries
        .sortedByDescending { it.value }
        .forEach {
            println("${it.key}: ${it.value}")
        }
}

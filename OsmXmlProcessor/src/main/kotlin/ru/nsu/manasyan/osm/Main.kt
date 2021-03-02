package ru.nsu.manasyan.osm

import org.slf4j.LoggerFactory
import ru.nsu.manasyan.osm.processor.StaxOsmXmlProcessor
import java.nio.file.NoSuchFileException
import javax.xml.stream.XMLStreamException

fun main(args: Array<String>) {
    val log = LoggerFactory.getLogger("Main")
    try {
        log.info("Starting file processing")
        val result = StaxOsmXmlProcessor().process(
            ArgsResolver.getInputFilePath(args)
        )
        println("User edits: ")
        printStats(result.editsByUser)
        println("Node counts by tag: ")
        printStats(result.nodesCountByTag)
    } catch (exc: WrongArgumentException) {
        log.error(exc.localizedMessage)
        println(ArgsResolver.usage())
    } catch (exc: XMLStreamException) {
        log.error("Error reading from XML")
    } catch (exc: NoSuchFileException) {
        log.error("No file was found with the provided path")
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


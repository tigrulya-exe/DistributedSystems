package ru.nsu.manasyan.osm

import org.slf4j.LoggerFactory
import java.io.FileNotFoundException
import javax.xml.stream.XMLStreamException

class Main {
    companion object {
        val logger = LoggerFactory.getLogger(Main::class.java)
    }
}

fun main(args: Array<String>) {
    try {
        val result = OsmXmlProcessor().process(
            ArgsResolver.getInputFilePath(args)
        )
        println("User edits: ")
        result.editsByUser.print()
        println("Node counts by tag: ")
        result.nodesCountByTag.print()
    } catch (exc: WrongArgumentException) {
        Main.logger.error(exc.localizedMessage)
        println(ArgsResolver.usage())
    } catch (exc: XMLStreamException) {
        Main.logger.error("Error reading from XML")
    } catch (exc: FileNotFoundException) {
        Main.logger.error("File at provided path not found")
    }
}


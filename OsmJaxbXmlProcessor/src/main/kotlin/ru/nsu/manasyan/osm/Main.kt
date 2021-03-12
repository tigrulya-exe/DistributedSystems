package ru.nsu.manasyan.osm

import ru.nsu.manasyan.osm.db.DbInitializer
import ru.nsu.manasyan.osm.db.dao.node.BatchNodeDao
import ru.nsu.manasyan.osm.db.dao.tag.BatchTagDao
import ru.nsu.manasyan.osm.db.datasource.HikariConnectionManager
import ru.nsu.manasyan.osm.db.transaction.PropagationTransactionManager
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.processor.OsmXmlProcessor
import ru.nsu.manasyan.osm.service.BatchNodeService
import java.nio.file.NoSuchFileException
import javax.xml.stream.XMLStreamException

fun main(args: Array<String>) {
    try {
        val wrapper = TransactionWrapper(
            PropagationTransactionManager(
                HikariConnectionManager
            )
        )
        DbInitializer(wrapper).initDb()

        OsmXmlProcessor.process(
            ArgsResolver.getInputFilePath(args),
            BatchNodeService(
                wrapper,
                BatchNodeDao(wrapper),
                BatchTagDao(wrapper)
            )
        )
    } catch (exc: WrongArgumentException) {
//        log.error(exc.localizedMessage)
        println(ArgsResolver.usage())
    } catch (exc: XMLStreamException) {
//        log.error("Error reading from XML")
    } catch (exc: NoSuchFileException) {
//        log.error("No file was found with the provided path")
    }
}

package ru.nsu.manasyan.osm

import org.postgresql.Driver
import ru.nsu.manasyan.osm.db.DbInitializer
import ru.nsu.manasyan.osm.db.dao.StatementOsmDao
import ru.nsu.manasyan.osm.model.generated.Node
import ru.nsu.manasyan.osm.processor.OsmXmlProcessor
import ru.nsu.manasyan.osm.processor.XmlProcessor
import java.nio.file.NoSuchFileException
import java.sql.DriverManager
import javax.xml.stream.XMLStreamException

fun main(args: Array<String>) {
    try {
        DbInitializer().initDb()
        OsmXmlProcessor.process(
            ArgsResolver.getInputFilePath(args),
            StatementOsmDao()
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

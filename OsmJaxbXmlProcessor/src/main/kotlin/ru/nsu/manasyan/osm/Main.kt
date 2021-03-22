package ru.nsu.manasyan.osm

import org.slf4j.LoggerFactory
import ru.nsu.manasyan.osm.db.datasource.SingleConnectionManager
import ru.nsu.manasyan.osm.test.NodeServiceInsertPerformanceTest

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger("Main")
    try {
        val application = Application(
            connectionManagerProvider = ::SingleConnectionManager
        )
        NodeServiceInsertPerformanceTest(
            application,
            ArgsResolver.getInputFilePath(args)
        ).run()
    } catch (exc: WrongArgumentException) {
        logger.error(exc.localizedMessage)
        println(ArgsResolver.usage())
    } catch (exc: Exception) {
        logger.error("Error during Application initialization: ${exc.localizedMessage}")
        exc.printStackTrace()
    }
}

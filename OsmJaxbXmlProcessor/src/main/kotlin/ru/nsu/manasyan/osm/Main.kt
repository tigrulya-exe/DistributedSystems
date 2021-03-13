package ru.nsu.manasyan.osm

import ru.nsu.manasyan.osm.db.dao.OsmDaoFactory
import ru.nsu.manasyan.osm.util.LoggerProperty

fun main(args: Array<String>) {
    val logger = object {
        val log by LoggerProperty()
    }

    try {
        Application().process(
            ArgsResolver.getInputFilePath(args),
            OsmDaoFactory.Strategy.BATCH
        )
    } catch (exc: WrongArgumentException) {
        logger.log.error(exc.localizedMessage)
        println(ArgsResolver.usage())
    } catch (exc: Exception) {
        logger.log.error("Error during Application initialization: ${exc.localizedMessage}")
        exc.printStackTrace()
    }
}

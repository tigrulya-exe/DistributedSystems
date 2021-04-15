package ru.nsu.manasyan.osm.test

import ru.nsu.manasyan.osm.Application
import ru.nsu.manasyan.osm.db.DbInitializer
import ru.nsu.manasyan.osm.db.dao.NodeServiceFactory
import ru.nsu.manasyan.osm.db.datasource.SingleConnectionManager
import ru.nsu.manasyan.osm.properties.DbPropertiesParser
import ru.nsu.manasyan.osm.util.LoggerProperty
import kotlin.system.measureTimeMillis

class NodeServiceInsertPerformanceTest(
    private val inputFilePath: String
) {
    private val logger by LoggerProperty()

    private val application: Application

    private val dbInitializer: DbInitializer

    init {
        val properties = DbPropertiesParser.parse()
        application = Application(
            properties,
            connectionManagerProvider = ::SingleConnectionManager
        )
        dbInitializer = DbInitializer(
            application.transactionManager,
            properties
        )
    }

    fun run() {
        runInsertBatch()
//        runInsertPreparedStatement()
//        runInsertStatement()
        application.clear()
    }

    private fun runInsertBatch() = runMeasuring("INSERT BATCH") {
        application.process(
            inputFilePath = inputFilePath,
            strategy = NodeServiceFactory.Strategy.BATCH
        )
    }

    private fun runInsertPreparedStatement() = runMeasuring("INSERT PREPARED STATEMENT") {
        application.process(
            inputFilePath = inputFilePath,
            strategy = NodeServiceFactory.Strategy.PREPARED_STATEMENT
        )
    }

    private fun runInsertStatement() = runMeasuring("INSERT STATEMENT") {
        application.process(
            inputFilePath = inputFilePath,
            strategy = NodeServiceFactory.Strategy.STATEMENT
        )
    }

    private fun runMeasuring(
        actionName: String,
        action: () -> Unit,
    ) {
        logger.info("Start action $actionName")
        logger.info("Step 1: init database")
        dbInitializer.initDb()
        logger.info("Step 2: run action")
        measureTimeMillis {
            action()
        }.let {
            logger.info("Elapsed time: %.3f sec".format(it / 1000F))
        }
    }
}
package ru.nsu.manasyan.osm.test

import ru.nsu.manasyan.osm.Application
import ru.nsu.manasyan.osm.db.dao.NodeServiceFactory
import ru.nsu.manasyan.osm.util.LoggerProperty
import kotlin.system.measureTimeMillis

class NodeServiceInsertPerformanceTest(
    private val application: Application,
    private val inputFilePath: String
) {
    private val logger by LoggerProperty()

    fun run() {
        runInsertBatch()
        runInsertPreparedStatement()
        runInsertStatement()
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
        application.dbInitializer.initDb()
        logger.info("Step 2: run action")
        measureTimeMillis {
            action()
        }.let {
            logger.info("Elapsed time: ${"%.3fs".format(it / 1000.0)}")
        }
    }
}
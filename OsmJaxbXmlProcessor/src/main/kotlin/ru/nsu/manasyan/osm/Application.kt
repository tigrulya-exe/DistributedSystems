package ru.nsu.manasyan.osm

import ru.nsu.manasyan.osm.db.DbInitializer
import ru.nsu.manasyan.osm.db.dao.NodeServiceFactory
import ru.nsu.manasyan.osm.db.datasource.ConnectionManager
import ru.nsu.manasyan.osm.db.datasource.HikariConnectionManager
import ru.nsu.manasyan.osm.db.transaction.SingleConnectionTransactionManager
import ru.nsu.manasyan.osm.processor.OsmXmlProcessor
import ru.nsu.manasyan.osm.properties.DbProperties
import ru.nsu.manasyan.osm.util.LoggerProperty

typealias ConnectionManagerProvider = (DbProperties) -> ConnectionManager

class Application(
    properties: DbProperties,
    connectionManagerProvider: ConnectionManagerProvider = ::HikariConnectionManager,
    initDb: Boolean = false
) {
    private val log by LoggerProperty()

    private val connectionManager = connectionManagerProvider(properties)

    val transactionManager = SingleConnectionTransactionManager(
        connectionManager
    )

    init {
        if (initDb) {
            DbInitializer(
                transactionManager,
                properties
            ).initDb()
        }
    }

    fun process(
        inputFilePath: String,
        strategy: NodeServiceFactory.Strategy = NodeServiceFactory.Strategy.BATCH
    ) {
        log.info("Start processing file $inputFilePath using strategy $strategy")
        try {
            NodeServiceFactory.createService(
                strategy,
                transactionManager
            ).use {
                OsmXmlProcessor.process(inputFilePath, it)
            }
            log.info("End processing file $inputFilePath using strategy $strategy")
        } catch (exc: Exception) {
            log.error("Error during xml processing: ${exc.localizedMessage}")
        }
    }

    fun clear() = connectionManager.close()
}
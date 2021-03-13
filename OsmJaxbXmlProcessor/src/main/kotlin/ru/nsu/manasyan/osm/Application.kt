package ru.nsu.manasyan.osm

import ru.nsu.manasyan.osm.db.DbInitializer
import ru.nsu.manasyan.osm.db.dao.OsmDaoFactory
import ru.nsu.manasyan.osm.db.datasource.ConnectionManager
import ru.nsu.manasyan.osm.db.datasource.HikariConnectionManager
import ru.nsu.manasyan.osm.db.transaction.PropagationTransactionManager
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.processor.OsmXmlProcessor
import ru.nsu.manasyan.osm.properties.DbProperties
import ru.nsu.manasyan.osm.properties.DbPropertiesParser
import ru.nsu.manasyan.osm.service.BatchNodeService
import ru.nsu.manasyan.osm.util.LoggerProperty
import ru.nsu.manasyan.osm.util.OncePerProgram

typealias ConnectionManagerProvider = (DbProperties) -> ConnectionManager

class Application(
    propertiesPath: String = "application.yaml",
    connectionManagerProvider: ConnectionManagerProvider = ::HikariConnectionManager
) {
    private val log by LoggerProperty()

    private val properties = DbPropertiesParser.parse(propertiesPath)

    private val transactionWrapper = TransactionWrapper(
        PropagationTransactionManager(
            connectionManagerProvider(properties)
        )
    )

    private val dbInitializer = DbInitializer(
        transactionWrapper,
        properties
    )

    init {
        OncePerProgram.run {
            dbInitializer.initDb()
        }
    }

    fun process(
        inputFilePath: String,
        strategy: OsmDaoFactory.Strategy = OsmDaoFactory.Strategy.BATCH
    ) {
        log.info("Start processing file $inputFilePath using strategy $strategy")
        try {
            OsmXmlProcessor.process(
                inputFilePath,
                BatchNodeService(
                    transactionWrapper,
                    strategy
                )
            )
            log.info("End processing file $inputFilePath using strategy $strategy")
        } catch (exc: Exception) {
            log.error("Error during xml processing: ${exc.localizedMessage}")
        }
    }
}
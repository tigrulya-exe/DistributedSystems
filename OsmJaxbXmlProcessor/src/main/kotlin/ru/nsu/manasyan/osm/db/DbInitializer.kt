package ru.nsu.manasyan.osm.db

import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.properties.DbProperties
import ru.nsu.manasyan.osm.util.LoggerProperty
import java.nio.file.Files
import java.nio.file.Path

class DbInitializer(
    private val manager: TransactionManager,
    private val properties: DbProperties
) {
    private val log by LoggerProperty()

    fun initDb() {
        val script = Files.readString(
            Path.of(
                this.javaClass
                    .classLoader
                    .getResource(properties.initScriptPath)?.toURI()
            )
        )

        log.info("Initializing db using script ${properties.initScriptPath}")
        manager.runInTransaction {
            createStatement().use { statement ->
                statement.execute(script)
            }
        }
        log.info("Db Initialised successfully")
    }
}
package ru.nsu.manasyan.osm.db

import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.properties.DbProperties
import ru.nsu.manasyan.osm.util.LoggerProperty
import java.nio.file.Files
import java.nio.file.Path

class DbInitializer(
    private val wrapper: TransactionWrapper,
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
        wrapper.runInTransaction {
            createStatement().use { statement ->
                statement.execute(script)
            }
        }
        log.info("Db Initialised successfully")
    }
}
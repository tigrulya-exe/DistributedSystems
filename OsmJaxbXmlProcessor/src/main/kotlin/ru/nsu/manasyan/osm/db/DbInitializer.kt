package ru.nsu.manasyan.osm.db

import ru.nsu.manasyan.osm.db.datasource.HikariCpDataSource
import ru.nsu.manasyan.osm.util.DbProperties
import java.nio.file.Files
import java.nio.file.Path

class DbInitializer {
    fun initDb() {
        try {
            val script = Files.readString(
                Path.of(
                    this.javaClass
                        .classLoader
                        .getResource(DbProperties.initScriptPath)?.toURI()
                )
            )

            // TODO: create connection and run script
            HikariCpDataSource.getConnection().use { connection ->
                connection.createStatement().use { statement ->
                    statement.execute(script)
                }
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
}
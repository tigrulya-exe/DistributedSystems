package ru.nsu.manasyan.osm.db

import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.util.DbProperties
import java.nio.file.Files
import java.nio.file.Path

class DbInitializer(
    private val wrapper: TransactionWrapper
) {
    fun initDb() {
        try {
            val script = Files.readString(
                Path.of(
                    this.javaClass
                        .classLoader
                        .getResource(DbProperties.initScriptPath)?.toURI()
                )
            )

            wrapper.runInTransaction {
                createStatement().use { statement ->
                    statement.execute(script)
                }
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
        }
    }
}
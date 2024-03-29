package ru.nsu.manasyan.osm.db.datasource

import ru.nsu.manasyan.osm.properties.DbProperties
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class SingleConnectionManager(
    private val properties: DbProperties
) : ConnectionManager {
    private var connection: Connection? = null

    override fun getConnection(): Connection = connection
        ?: createConnection().also { connection = it }

    override fun closeConnection(connection: Connection) {}

    override fun close() {
        connection?.close()
    }

    private fun createConnection() = DriverManager.getConnection(
        properties.jdbcUrl,
        Properties().apply {
            setProperty("user", properties.userName)
            setProperty("password", properties.password)
            setProperty("prepareThreshold", "1")
        }
    )
}
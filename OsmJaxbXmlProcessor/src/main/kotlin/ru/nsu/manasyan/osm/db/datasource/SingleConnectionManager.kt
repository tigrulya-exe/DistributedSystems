package ru.nsu.manasyan.osm.db.datasource

import ru.nsu.manasyan.osm.util.DbProperties
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class SingleConnectionManager : ConnectionManager {
    private val connection: Connection? = null

    override fun getConnection(): Connection = connection ?: createConnection()

    private fun createConnection() = DriverManager.getConnection(
        DbProperties.jdbcUrl,
        Properties().apply {
            setProperty("user", DbProperties.userName)
            setProperty("password", DbProperties.password)
            setProperty("prepareThreshold", "1")
        }
    )
}
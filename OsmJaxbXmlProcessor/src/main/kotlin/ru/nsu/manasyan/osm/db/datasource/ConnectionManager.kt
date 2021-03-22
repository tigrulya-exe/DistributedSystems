package ru.nsu.manasyan.osm.db.datasource

import java.sql.Connection

interface ConnectionManager : AutoCloseable {
    fun getConnection(): Connection
    fun closeConnection(connection: Connection)
}
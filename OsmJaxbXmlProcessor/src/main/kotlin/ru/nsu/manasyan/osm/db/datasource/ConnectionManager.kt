package ru.nsu.manasyan.osm.db.datasource

import java.sql.Connection

interface ConnectionManager {
    fun getConnection(): Connection
}
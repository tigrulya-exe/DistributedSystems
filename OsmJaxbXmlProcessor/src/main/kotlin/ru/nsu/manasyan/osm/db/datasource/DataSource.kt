package ru.nsu.manasyan.osm.db.datasource

import java.sql.Connection

interface DataSource {
    fun getConnection(): Connection
}
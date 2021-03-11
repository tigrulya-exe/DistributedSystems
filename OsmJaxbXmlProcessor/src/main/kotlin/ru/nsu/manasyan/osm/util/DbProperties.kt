package ru.nsu.manasyan.osm.util

object DbProperties {
    val jdbcUrl: String
    val userName: String
    val password: String
    val initScriptPath: String

    init {
        // TODO: read from db.properties file
        jdbcUrl = "jdbc:postgresql://localhost:5432/Osm"
        userName = "postgres"
        password = "password"
        initScriptPath = "init.sql"
    }
}
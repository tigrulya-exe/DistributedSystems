package ru.nsu.manasyan.osm.properties

class DbProperties(
    var jdbcUrl: String = "jdbc:postgresql://localhost:5432/Osm",
    var userName: String = "postgres",
    var password: String = "password",
    var initScriptPath: String = "init.sql"
)
package ru.nsu.manasyan.osm.db.datasource

import ru.nsu.manasyan.osm.util.DbProperties
import java.sql.Connection
import java.sql.DriverManager

object DriverManagerDataSource : DataSource {
    override fun getConnection(): Connection = DriverManager.getConnection(
        DbProperties.jdbcUrl,
        DbProperties.userName,
        DbProperties.password
    )
}
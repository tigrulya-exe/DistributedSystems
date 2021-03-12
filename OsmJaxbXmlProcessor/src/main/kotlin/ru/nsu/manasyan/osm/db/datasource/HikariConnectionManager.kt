package ru.nsu.manasyan.osm.db.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import ru.nsu.manasyan.osm.util.DbProperties
import java.sql.Connection

object HikariConnectionManager : ConnectionManager {
    private val config = HikariConfig()

    private val ds: HikariDataSource = HikariDataSource(
        config.apply {
            jdbcUrl = DbProperties.jdbcUrl
            username = DbProperties.userName
            password = DbProperties.password
            // TODO: get custom props from .properties file
            // enable postgresql server-side statement-caching
            dataSourceProperties["prepareThreshold"] = "1"
        }
    )

    override fun getConnection(): Connection = ds.connection
}
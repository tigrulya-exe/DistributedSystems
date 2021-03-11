package ru.nsu.manasyan.osm.db.datasource

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import ru.nsu.manasyan.osm.util.DbProperties
import java.sql.Connection


object HikariCpDataSource : DataSource {
    private val config = HikariConfig()
    private val ds: HikariDataSource

    init {
        config.jdbcUrl = DbProperties.jdbcUrl
        config.username = DbProperties.userName
        config.password = DbProperties.password
        config.dataSourceProperties["cachePrepStmts"] = "true"
        config.dataSourceProperties["prepStmtCacheSize"] = "250"
        config.dataSourceProperties["prepStmtCacheSqlLimit"] = "2048"
        ds = HikariDataSource(config)
    }

    override fun getConnection(): Connection = ds.connection
}

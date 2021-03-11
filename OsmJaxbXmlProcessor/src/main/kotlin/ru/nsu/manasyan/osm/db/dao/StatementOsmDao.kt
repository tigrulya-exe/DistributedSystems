package ru.nsu.manasyan.osm.db.dao

import ru.nsu.manasyan.osm.db.datasource.HikariCpDataSource
import ru.nsu.manasyan.osm.model.Node
import ru.nsu.manasyan.osm.model.Tag
import java.time.format.DateTimeFormatter

class StatementOsmDao : OsmDao {
    override fun saveNode(node: Node) {
        HikariCpDataSource.getConnection().use { connection ->
            connection.createStatement().use { statement ->
                statement.execute("""
                    INSERT INTO NODES VALUES(
                        ${node.id},
                        '${node.user}',
                        ${node.uid},
                        ${node.lat},
                        ${node.lon},
                        ${node.version},
                        ${node.changeset},
                        '${node.timestamp}'
                    )
                """.trimIndent())

            }
        }
    }

    override fun saveTag(tag: Tag) {
        HikariCpDataSource.getConnection().use { connection ->
            connection.createStatement().use { statement ->
                statement.executeQuery("""
                    INSERT INTO TAGS(key, value, nodeId) VALUES(
                        ${tag.key},
                        ${tag.value},
                        ${tag.nodeId}
                    )
                """.trimIndent())
            }
        }
    }
}
package ru.nsu.manasyan.osm.db.dao.node

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Node

class StatementNodeDao(
    private val transactionWrapper: TransactionWrapper
) : OsmDao<Node> {
    override fun save(entity: Node) {
        transactionWrapper.runInTransaction {
            createStatement().use { statement ->
                statement.execute(
                    """INSERT INTO NODES VALUES(
                        ${entity.id},
                        '${entity.user}',
                        ${entity.uid},
                        ${entity.lat},
                        ${entity.lon},
                        ${entity.version},
                        ${entity.changeset},
                        '${entity.timestamp}'
                    )""".trimIndent()
                )
            }
        }
    }
}
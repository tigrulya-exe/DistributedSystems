package ru.nsu.manasyan.osm.db.dao.node

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.NodeEntity
import ru.nsu.manasyan.osm.util.escapeQuotes

class StatementNodeDao(
    transactionManager: TransactionManager
) : OsmDao<NodeEntity> {
    private val statement = transactionManager.runInTransaction {
        createStatement()
    }

    override fun save(entity: NodeEntity) {
        statement.execute(
            """INSERT INTO NODES VALUES(
                ${entity.id},
                '${entity.user.escapeQuotes()}',
                ${entity.latitude},
                ${entity.longitude}
            )""".trimIndent()
        )
    }

    override fun close() = statement.close()
}
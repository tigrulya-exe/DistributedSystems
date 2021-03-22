package ru.nsu.manasyan.osm.db.dao.node

import ru.nsu.manasyan.osm.db.dao.SingleConnectionOsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.Node

class StatementNodeDao(
    transactionManager: TransactionManager
) : SingleConnectionOsmDao<Node> {
    private val statement = transactionManager.runInTransaction {
        createStatement()
    }

    override fun save(entity: Node) {
        statement.execute(
            """INSERT INTO NODES VALUES(
                ${entity.id},
                '${entity.user}',
                ${entity.latitude},
                ${entity.longitude}
            )""".trimIndent()
        )
    }

    override fun close() = statement.close()
}
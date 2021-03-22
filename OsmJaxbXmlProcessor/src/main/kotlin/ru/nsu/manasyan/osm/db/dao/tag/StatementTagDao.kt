package ru.nsu.manasyan.osm.db.dao.tag

import ru.nsu.manasyan.osm.db.dao.SingleConnectionOsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.Tag

class StatementTagDao(
    transactionManager: TransactionManager
) : SingleConnectionOsmDao<Tag> {

    private val statement = transactionManager.runInTransaction {
        createStatement()
    }

    override fun save(entity: Tag) {
        statement.execute(
            """INSERT INTO TAGS(key, value, nodeId) VALUES(
                    '${entity.key}',
                    '${entity.value}',
                    ${entity.nodeId}
            )""".trimIndent()
        )
    }

    override fun close() = statement.close()
}
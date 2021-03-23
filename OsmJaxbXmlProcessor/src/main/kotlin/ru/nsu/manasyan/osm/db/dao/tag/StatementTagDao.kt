package ru.nsu.manasyan.osm.db.dao.tag

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.TagEntity
import ru.nsu.manasyan.osm.util.escapeQuotes

class StatementTagDao(
    transactionManager: TransactionManager
) : OsmDao<TagEntity> {

    private val statement = transactionManager.runInTransaction {
        createStatement()
    }

    override fun save(entity: TagEntity) {
        statement.execute(
            """INSERT INTO TAGS(key, value, nodeId) VALUES(
                    '${entity.key?.escapeQuotes()}',
                    '${entity.value?.escapeQuotes()}',
                    ${entity.nodeId}
            )""".trimIndent()
        )
    }

    override fun close() = statement.close()
}
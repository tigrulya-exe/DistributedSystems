package ru.nsu.manasyan.osm.db.dao.tag

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Tag

class StatementTagDao(
    private val transactionWrapper: TransactionWrapper
) : OsmDao<Tag> {

    override fun save(entity: Tag) {
        transactionWrapper.runInTransaction {
            createStatement().use { statement ->
                statement.execute(
                    """INSERT INTO TAGS(key, value, nodeId) VALUES(
                        '${entity.key}',
                        '${entity.value}',
                        ${entity.nodeId}
                    )""".trimIndent()
                )
            }
        }
    }

}
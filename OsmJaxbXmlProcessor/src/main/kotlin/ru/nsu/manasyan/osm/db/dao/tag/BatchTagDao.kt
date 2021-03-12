package ru.nsu.manasyan.osm.db.dao.tag

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.dao.tag.PreparedStatementTagDao.Companion.PREPARED_STATEMENT
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Tag
import java.sql.PreparedStatement

class BatchTagDao(
    private val transactionWrapper: TransactionWrapper
) : OsmDao<Tag> {

    private val backedDao = PreparedStatementTagDao(transactionWrapper)

    // just delegate to backedDao
    override fun save(entity: Tag) = backedDao.save(entity)

    override fun saveAll(entities: Iterable<Tag>) {
        transactionWrapper.runInTransaction {
            prepareStatement(PREPARED_STATEMENT).use { statement ->
                addBatches(statement, entities)
                statement.executeBatch()
            }
        }
    }

    private fun addBatches(statement: PreparedStatement, tags: Iterable<Tag>) {
        tags.forEach { tag ->
            backedDao.setStatementVariables(statement, tag)
            statement.addBatch()
        }
    }
}
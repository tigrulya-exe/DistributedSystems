package ru.nsu.manasyan.osm.db.dao.tag

import ru.nsu.manasyan.osm.db.dao.SingleConnectionOsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.Tag
import java.sql.PreparedStatement

class BatchTagDao(
    private val transactionWrapper: TransactionManager
) : SingleConnectionOsmDao<Tag> {

    private val dao = PreparedStatementTagDao(transactionWrapper)

    override fun save(entity: Tag) = dao.save(entity)

    override fun saveAll(entities: Iterable<Tag>) {
        dao.preparedStatement.let { statement ->
            addBatches(statement, entities)
            statement.executeBatch()
        }
    }

    private fun addBatches(statement: PreparedStatement, tags: Iterable<Tag>) {
        tags.forEach { tag ->
            dao.setStatementVariables(statement, tag)
            statement.addBatch()
        }
    }

    override fun close() = dao.close()
}
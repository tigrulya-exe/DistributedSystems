package ru.nsu.manasyan.osm.db.dao.node

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.dao.node.PreparedStatementNodeDao.Companion.PREPARED_STATEMENT
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Node
import java.sql.PreparedStatement

class BatchNodeDao(
    private val transactionWrapper: TransactionWrapper
) : OsmDao<Node> {
    private val backedDao = PreparedStatementNodeDao(transactionWrapper)

    // just delegate to backedDao
    override fun save(entity: Node) = backedDao.save(entity)

    override fun saveAll(entities: Iterable<Node>) {
        transactionWrapper.runInTransaction {
            prepareStatement(PREPARED_STATEMENT).use { statement ->
                addBatches(statement, entities)
                statement.executeBatch()
            }
        }
    }

    private fun addBatches(statement: PreparedStatement, nodes: Iterable<Node>) {
        nodes.forEach { node ->
            backedDao.setStatementVariables(statement, node)
            statement.addBatch()
        }
    }
}
package ru.nsu.manasyan.osm.db.dao.node

import ru.nsu.manasyan.osm.db.dao.SingleConnectionOsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.Node
import java.sql.PreparedStatement

class BatchNodeDao(
    transactionManager: TransactionManager
) : SingleConnectionOsmDao<Node> {
    private val dao = PreparedStatementNodeDao(transactionManager)

    override fun save(entity: Node) = dao.save(entity)

    override fun saveAll(entities: Iterable<Node>) {
        dao.preparedStatement.let { statement ->
            addBatches(statement, entities)
            statement.executeBatch()
        }
    }

    private fun addBatches(statement: PreparedStatement, nodes: Iterable<Node>) {
        nodes.forEach { node ->
            dao.setStatementVariables(statement, node)
            statement.addBatch()
        }
    }

    override fun close() = dao.close()
}
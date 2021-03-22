package ru.nsu.manasyan.osm.db.dao.node

import ru.nsu.manasyan.osm.db.dao.SingleConnectionOsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.Node
import java.sql.PreparedStatement

class PreparedStatementNodeDao(
    transactionManager: TransactionManager
) : SingleConnectionOsmDao<Node> {
    companion object {
        const val PREPARED_STATEMENT = "INSERT INTO NODES VALUES(?,?,?,?)"
    }

    val preparedStatement = transactionManager.runInTransaction {
        prepareStatement(PREPARED_STATEMENT)
    }

    override fun save(entity: Node) {
        setStatementVariables(preparedStatement, entity).executeUpdate()
    }

    fun setStatementVariables(statement: PreparedStatement, node: Node) = statement.apply {
        setObject(1, node.id)
        setString(2, node.user)
        setDouble(3, node.latitude)
        setDouble(4, node.longitude)
    }

    override fun close() = preparedStatement.close()
}
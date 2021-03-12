package ru.nsu.manasyan.osm.db.dao.node

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Node
import java.sql.PreparedStatement

class PreparedStatementNodeDao(
    private val transactionWrapper: TransactionWrapper
) : OsmDao<Node> {
    companion object {
        const val PREPARED_STATEMENT = "INSERT INTO NODES VALUES(?,?,?,?,?,?,?,?)"
    }

    override fun save(entity: Node) {
        transactionWrapper.runInTransaction {
            prepareStatement(PREPARED_STATEMENT).use { statement ->
                setStatementVariables(statement, entity)
                statement.execute()
            }
        }
    }

    fun setStatementVariables(statement: PreparedStatement, node: Node) = statement.apply {
        setObject(1, node.id)
        setString(2, node.user)
        setObject(3, node.uid)
        setDouble(5, node.lon)
        setDouble(4, node.lat)
        setObject(6, node.version)
        setObject(7, node.changeset)
        setObject(8, node.timestamp)
    }
}
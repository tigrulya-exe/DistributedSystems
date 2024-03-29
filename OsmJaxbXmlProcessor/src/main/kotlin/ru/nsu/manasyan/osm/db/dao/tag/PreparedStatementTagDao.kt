package ru.nsu.manasyan.osm.db.dao.tag

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.TagEntity
import java.sql.PreparedStatement

class PreparedStatementTagDao(
    transactionManager: TransactionManager
) : OsmDao<TagEntity> {
    companion object {
        const val PREPARED_STATEMENT = "INSERT INTO TAGS(key, value, nodeid) VALUES(?,?,?)"
    }

    val preparedStatement = transactionManager.runInTransaction {
        prepareStatement(PREPARED_STATEMENT)
    }

    override fun save(entity: TagEntity) {
        setStatementVariables(preparedStatement, entity)
        preparedStatement.executeUpdate()
    }

    fun setStatementVariables(statement: PreparedStatement, tag: TagEntity) = statement.apply {
        setString(1, tag.key)
        setString(2, tag.value)
        setObject(3, tag.nodeId)
    }

    override fun close() = preparedStatement.close()
}
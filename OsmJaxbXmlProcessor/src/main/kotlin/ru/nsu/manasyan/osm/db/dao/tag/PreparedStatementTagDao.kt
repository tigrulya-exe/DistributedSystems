package ru.nsu.manasyan.osm.db.dao.tag

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Tag
import java.sql.PreparedStatement

class PreparedStatementTagDao(
    private val transactionWrapper: TransactionWrapper
) : OsmDao<Tag> {
    companion object {
        const val PREPARED_STATEMENT = "INSERT INTO TAGS(key, value, nodeid) VALUES(?,?,?)"
    }

    override fun save(entity: Tag) {
        transactionWrapper.runInTransaction {
            prepareStatement(PREPARED_STATEMENT).use { statement ->
                setStatementVariables(statement, entity)
                statement.execute()
            }
        }
    }

    fun setStatementVariables(statement: PreparedStatement, tag: Tag) = statement.apply {
        setString(1, tag.key)
        setString(2, tag.value)
        setObject(3, tag.nodeId)
    }
}
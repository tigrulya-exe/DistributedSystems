package ru.nsu.manasyan.osm.db.dao

import ru.nsu.manasyan.osm.db.dao.node.BatchNodeDao
import ru.nsu.manasyan.osm.db.dao.node.PreparedStatementNodeDao
import ru.nsu.manasyan.osm.db.dao.node.StatementNodeDao
import ru.nsu.manasyan.osm.db.dao.tag.BatchTagDao
import ru.nsu.manasyan.osm.db.dao.tag.PreparedStatementTagDao
import ru.nsu.manasyan.osm.db.dao.tag.StatementTagDao
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Node
import ru.nsu.manasyan.osm.model.Tag

object OsmDaoFactory {
    enum class Strategy {
        STATEMENT,
        PREPARED_STATEMENT,
        BATCH
    }

    private val creators = mapOf(
        Strategy.STATEMENT to OsmDaoCreators(::StatementNodeDao, ::StatementTagDao),
        Strategy.PREPARED_STATEMENT to OsmDaoCreators(::PreparedStatementNodeDao, ::PreparedStatementTagDao),
        Strategy.BATCH to OsmDaoCreators(::BatchNodeDao, ::BatchTagDao)
    )

    fun createNodeDao(strategy: Strategy, tw: TransactionWrapper): OsmDao<Node> =
        createDao(strategy, tw) { nodeDaoCreator(it) }

    fun createTagDao(strategy: Strategy, tw: TransactionWrapper): OsmDao<Tag> =
        createDao(strategy, tw) { tagDaoCreator(it) }

    private inline fun <E> createDao(
        strategy: Strategy,
        tw: TransactionWrapper,
        creator: OsmDaoCreators.(TransactionWrapper) -> OsmDao<E>
    ): OsmDao<E> {
        return creators[strategy]?.let {
            it.creator(tw)
        } ?: throw IllegalArgumentException("Wrong strategy: $strategy")
    }
}

private class OsmDaoCreators(
    val nodeDaoCreator: (TransactionWrapper) -> OsmDao<Node>,
    val tagDaoCreator: (TransactionWrapper) -> OsmDao<Tag>
) {
}


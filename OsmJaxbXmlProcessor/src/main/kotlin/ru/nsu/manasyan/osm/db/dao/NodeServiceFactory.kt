package ru.nsu.manasyan.osm.db.dao

import ru.nsu.manasyan.osm.db.dao.node.BatchNodeDao
import ru.nsu.manasyan.osm.db.dao.node.PreparedStatementNodeDao
import ru.nsu.manasyan.osm.db.dao.node.StatementNodeDao
import ru.nsu.manasyan.osm.db.dao.tag.BatchTagDao
import ru.nsu.manasyan.osm.db.dao.tag.PreparedStatementTagDao
import ru.nsu.manasyan.osm.db.dao.tag.StatementTagDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.NodeEntity
import ru.nsu.manasyan.osm.model.TagEntity
import ru.nsu.manasyan.osm.service.BatchNodeService
import ru.nsu.manasyan.osm.service.NodeService

object NodeServiceFactory {
    enum class Strategy {
        STATEMENT,
        PREPARED_STATEMENT,
        BATCH
    }

    private val creators = mapOf(
        Strategy.STATEMENT to OsmDaoCreators(
            ::StatementNodeDao,
            ::StatementTagDao,
            ::NodeService
        ),
        Strategy.PREPARED_STATEMENT to OsmDaoCreators(
            ::PreparedStatementNodeDao,
            ::PreparedStatementTagDao,
            ::NodeService
        ),
        Strategy.BATCH to OsmDaoCreators(
            ::BatchNodeDao,
            ::BatchTagDao,
            ::BatchNodeService
        )
    )

    fun createService(strategy: Strategy, manager: TransactionManager): NodeService {
        return creators[strategy]?.let {
            it.serviceCreator(
                manager,
                it.nodeDaoCreator(manager),
                it.tagDaoCreator(manager)
            )
        } ?: throw IllegalArgumentException("Wrong strategy: $strategy")
    }
}

private class OsmDaoCreators(
    val nodeDaoCreator: (TransactionManager) -> OsmDao<NodeEntity>,
    val tagDaoCreator: (TransactionManager) -> OsmDao<TagEntity>,
    val serviceCreator: (
        TransactionManager,
        OsmDao<NodeEntity>,
        OsmDao<TagEntity>
    ) -> NodeService
)


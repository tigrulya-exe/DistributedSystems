package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.dao.OsmDaoFactory
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Node
import ru.nsu.manasyan.osm.model.Tag

open class NodeService(
    private val transactionWrapper: TransactionWrapper,
    strategy: OsmDaoFactory.Strategy
) {
    protected val nodeDao: OsmDao<Node> = OsmDaoFactory.createNodeDao(strategy, transactionWrapper)
    protected val tagDao: OsmDao<Tag> = OsmDaoFactory.createTagDao(strategy, transactionWrapper)

    open fun save(entity: Node) {
        transactionWrapper.runInTransaction {
            nodeDao.save(entity)
            entity.tags.forEach(tagDao::save)
        }
    }
}
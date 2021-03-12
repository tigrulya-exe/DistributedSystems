package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Node
import ru.nsu.manasyan.osm.model.Tag

open class NodeService(
    private val transactionWrapper: TransactionWrapper,
    protected val nodeDao: OsmDao<Node>,
    protected val tagDao: OsmDao<Tag>
) {
    open fun save(entity: Node) {
        transactionWrapper.runInTransaction {
            nodeDao.save(entity)
            entity.tags.forEach(tagDao::save)
        }
    }
}
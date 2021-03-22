package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.db.dao.SingleConnectionOsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.Node
import ru.nsu.manasyan.osm.model.Tag

open class NodeService(
    private val transactionManager: TransactionManager,
    protected val nodeDao: SingleConnectionOsmDao<Node>,
    protected val tagDao: SingleConnectionOsmDao<Tag>
) : SingleConnectionNodeService {

    override fun save(entity: Node) {
        transactionManager.runInTransaction {
            nodeDao.save(entity)
            entity.tags.forEach(tagDao::save)
        }
    }

    override fun close() {}
}
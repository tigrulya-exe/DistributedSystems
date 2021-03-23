package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.NodeEntity
import ru.nsu.manasyan.osm.model.TagEntity

open class NodeService(
    private val transactionManager: TransactionManager,
    protected val nodeDao: OsmDao<NodeEntity>,
    protected val tagDao: OsmDao<TagEntity>
) : SingleConnectionNodeService {

    override fun save(entity: NodeEntity) {
        transactionManager.runInTransaction {
            nodeDao.save(entity)
            entity.tags.forEach(tagDao::save)
        }
    }

    override fun close() {}
}
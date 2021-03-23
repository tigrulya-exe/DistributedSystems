package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.db.dao.OsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.NodeEntity
import ru.nsu.manasyan.osm.model.TagEntity

class BatchNodeService(
    private val transactionManager: TransactionManager,
    nodeDao: OsmDao<NodeEntity>,
    tagDao: OsmDao<TagEntity>,
    private val batchSize: Int = 64000
) : NodeService(transactionManager, nodeDao, tagDao) {

    private val batch = mutableListOf<NodeEntity>()

    override fun save(entity: NodeEntity) {
        batch.add(entity)
        if (batch.size == batchSize) {
            flush()
        }
    }

    override fun close() {
        if (batch.isNotEmpty()) {
            flush()
        }
        super.close()
    }

    private fun flush() {
        transactionManager.runInTransaction {
            nodeDao.saveAll(batch)
            tagDao.saveAll(
                batch.flatMap(NodeEntity::tags)
            )
        }
        batch.clear()
    }
}
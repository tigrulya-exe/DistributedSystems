package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.db.dao.SingleConnectionOsmDao
import ru.nsu.manasyan.osm.db.transaction.TransactionManager
import ru.nsu.manasyan.osm.model.Node
import ru.nsu.manasyan.osm.model.Tag

class BatchNodeService(
    private val transactionManager: TransactionManager,
    nodeDao: SingleConnectionOsmDao<Node>,
    tagDao: SingleConnectionOsmDao<Tag>,
    private val batchSize: Int = 50000
) : NodeService(transactionManager, nodeDao, tagDao) {

    private val batch = mutableListOf<Node>()

    override fun save(entity: Node) {
        batch.add(entity)
        if (batch.size == batchSize) {
            flush()
        }
    }

    override fun close() = flush()

    private fun flush() {
        transactionManager.runInTransaction {
            nodeDao.saveAll(batch)
            tagDao.saveAll(
                batch.flatMap(Node::tags)
            )
        }
        batch.clear()
    }
}
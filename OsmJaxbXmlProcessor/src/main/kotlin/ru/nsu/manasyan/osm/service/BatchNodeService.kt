package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.db.dao.node.BatchNodeDao
import ru.nsu.manasyan.osm.db.dao.tag.BatchTagDao
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Node

class BatchNodeService(
    private val transactionWrapper: TransactionWrapper,
    nodeDao: BatchNodeDao,
    tagDao: BatchTagDao,
    private val batchSize: Int = 100
) : NodeService(transactionWrapper, nodeDao, tagDao) {

    private val batch = mutableListOf<Node>()

    override fun save(entity: Node) {
        batch.add(entity)
        if (batch.size == batchSize) {
            flush()
        }
    }

    private fun flush() {
        transactionWrapper.runInTransaction {
            nodeDao.saveAll(batch)
            tagDao.saveAll(
                batch.flatMap(Node::tags)
            )
        }
        batch.clear()
    }
}
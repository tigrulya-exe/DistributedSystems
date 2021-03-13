package ru.nsu.manasyan.osm.service

import ru.nsu.manasyan.osm.db.dao.OsmDaoFactory
import ru.nsu.manasyan.osm.db.transaction.TransactionWrapper
import ru.nsu.manasyan.osm.model.Node

class BatchNodeService(
    private val transactionWrapper: TransactionWrapper,
    strategy: OsmDaoFactory.Strategy,
    private val batchSize: Int = 10000
) : NodeService(transactionWrapper, strategy) {

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
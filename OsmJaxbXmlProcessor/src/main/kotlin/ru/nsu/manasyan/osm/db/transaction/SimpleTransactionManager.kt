package ru.nsu.manasyan.osm.db.transaction

import ru.nsu.manasyan.osm.db.datasource.ConnectionManager

class SingleConnectionTransactionManager(
    private val connectionManager: ConnectionManager
) : TransactionManager {
    private var currentTransaction: Transaction? = null

    override fun getTransaction(): Transaction = currentTransaction ?: create()

    override fun commit(transaction: Transaction) {
        currentTransaction
            ?.connection
            ?.commit()
            ?: throw IllegalStateException("")
    }

    override fun rollback(transaction: Transaction) {
        currentTransaction
            ?.connection
            ?.rollback()
            ?: throw IllegalStateException("")
    }

    private fun create(): Transaction = Transaction(
        connectionManager.getConnection()
    ).also {
        it.connection.autoCommit = false
        currentTransaction = it
    }
}
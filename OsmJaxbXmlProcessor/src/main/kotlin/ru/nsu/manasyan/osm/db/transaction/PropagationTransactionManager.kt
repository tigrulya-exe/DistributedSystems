package ru.nsu.manasyan.osm.db.transaction

import ru.nsu.manasyan.osm.db.datasource.ConnectionManager

class PropagationTransactionManager(
    private val connectionManager: ConnectionManager
) : TransactionManager {

    private val openedTransactions = TreadLocalTransactionsContainer()

    override fun commit(transaction: Transaction) {
        openedTransactions.decrementTransactionsCount()
        if (!openedTransactions.isLastOpenedTransaction()) {
            return
        }
        transaction.connection.commit()
        close(transaction)
    }

    override fun rollback(transaction: Transaction) {
        openedTransactions.decrementTransactionsCount()
        if (!openedTransactions.isLastOpenedTransaction()) {
            return
        }
        transaction.connection.rollback()
        close(transaction)
    }

    override fun get(): Transaction {
        val transaction = openedTransactions.getCurrentTransaction() ?: create()
        openedTransactions.incrementTransactionsCount()
        return transaction
    }

    private fun close(transaction: Transaction) {
        connectionManager.closeConnection(transaction.connection)
        openedTransactions.removeCurrentTransaction()
    }

    private fun create(): Transaction = Transaction(
        connectionManager.getConnection()
    ).also {
        it.connection.autoCommit = false
        openedTransactions.setCurrentTransaction(it)
    }
}

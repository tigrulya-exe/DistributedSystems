package ru.nsu.manasyan.osm.db.transaction.propagation

import ru.nsu.manasyan.osm.db.datasource.ConnectionManager
import ru.nsu.manasyan.osm.db.transaction.Transaction
import ru.nsu.manasyan.osm.db.transaction.TransactionManager

class PropagationTransactionManager(
    private val connectionManager: ConnectionManager
) : TransactionManager {

    private val openedTransactions = TreadLocalTransactionsContainer()

    override fun commit(transaction: Transaction) {
        if (!openedTransactions.isLastOpenedTransaction()) {
            return
        }
        transaction.connection.commit()
        close(transaction)
    }

    override fun rollback(transaction: Transaction) {
        if (!openedTransactions.isLastOpenedTransaction()) {
            return
        }
        transaction.connection.rollback()
        close(transaction)
    }

    override fun getTransaction(): Transaction {
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

package ru.nsu.manasyan.osm.db.transaction.propagation

import ru.nsu.manasyan.osm.db.transaction.Transaction

class TreadLocalTransactionsContainer {
    private val openedTransactionContext = ThreadLocal.withInitial {
        TransactionContext()
    }

    fun setCurrentTransaction(transaction: Transaction) {
        getOpenedTransaction().openedTransaction = transaction
    }

    fun removeCurrentTransaction() {
        getOpenedTransaction().apply {
            openedTransaction = null
            decrementCount()
        }
    }

    fun getCurrentTransaction(): Transaction? = getOpenedTransaction().openedTransaction

    fun incrementTransactionsCount() = getOpenedTransaction().incrementCount()

    fun isLastOpenedTransaction() = getOpenedTransaction().openedTransactionsCount != 1

    private fun getOpenedTransaction() = openedTransactionContext.get()
}

data class TransactionContext(
    var openedTransaction: Transaction? = null,
    var openedTransactionsCount: Int = 0
) {
    fun incrementCount() = openedTransactionsCount++
    fun decrementCount() = openedTransactionsCount--
}
package ru.nsu.manasyan.osm.db.transaction

//TODO: refactor
class TreadLocalTransactionsContainer {
    private val openedTransaction = ThreadLocal.withInitial<Transaction?> { null }
    private val openedTransactionsCount = ThreadLocal.withInitial { 0 }

    fun setCurrentTransaction(transaction: Transaction) = openedTransaction.set(transaction)

    fun removeCurrentTransaction() = openedTransaction.remove()

    fun getCurrentTransaction(): Transaction? = openedTransaction.get()

    fun incrementTransactionsCount() = openedTransactionsCount.set(openedTransactionsCount.get() + 1)

    fun decrementTransactionsCount() = openedTransactionsCount.set(openedTransactionsCount.get() - 1)

    fun isLastOpenedTransaction() = openedTransactionsCount.get() != 0
}
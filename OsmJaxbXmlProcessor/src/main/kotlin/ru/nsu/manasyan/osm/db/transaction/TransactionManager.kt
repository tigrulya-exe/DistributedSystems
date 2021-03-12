package ru.nsu.manasyan.osm.db.transaction

import java.sql.Connection
import java.sql.SQLException

interface TransactionManager {
    fun get(): Transaction
    fun commit(transaction: Transaction)
    fun rollback(transaction: Transaction)
}

class TransactionWrapper(
    private val transactionManager: TransactionManager
) {
    fun runInTransaction(func: Connection.() -> Unit) {
        val transaction = transactionManager.get()
        try {
            println("START TRANSACTION")
            transaction.connection.func()
            transactionManager.commit(transaction)
        } catch (exc: Exception) {
            try {
                transactionManager.rollback(transaction)
            } catch (exc: SQLException) {
                // TODO: add logger
            }
            throw exc
        }.also {
            println("END TRANSACTION")
        }
    }
}
package ru.nsu.manasyan.osm.db.transaction

import ru.nsu.manasyan.osm.util.LoggerProperty
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
    private val log by LoggerProperty()

    fun runInTransaction(func: Connection.() -> Unit) {
        val transaction = transactionManager.get()
        try {
            transaction.connection.func()
            transactionManager.commit(transaction)
        } catch (exc: Exception) {
            try {
                log.error("Error during transaction: ${exc.localizedMessage}")
                transactionManager.rollback(transaction)
            } catch (exc: SQLException) {
                log.error("Error during transaction rollback: ${exc.localizedMessage}")
            }
            throw exc
        }
    }
}
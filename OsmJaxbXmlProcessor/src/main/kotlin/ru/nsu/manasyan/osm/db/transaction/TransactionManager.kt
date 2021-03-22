package ru.nsu.manasyan.osm.db.transaction

import java.sql.Connection

data class Transaction(val connection: Connection)

interface TransactionManager {
    fun getTransaction(): Transaction

    fun commit(transaction: Transaction)

    fun rollback(transaction: Transaction)

    fun <R> runInTransaction(action: Connection.() -> R): R {
        val transaction = getTransaction()
        try {
            return transaction.connection
                .action()
                .also {
                    commit(transaction)
                }
        } catch (exc: Exception) {
            rollback(transaction)
            throw exc
        }
    }
}
package com.onerep.data.db

import com.onerep.data.db.models.Users
import io.github.cdimascio.dotenv.dotenv
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {

        val dotenv = dotenv()

        val database =  Database.connect(
            url = dotenv["DATABASE"].toString(),
            driver = dotenv["DATABASE_DRIVER"].toString(),
            user = dotenv["DATABASE_USER"].toString(),
            password = dotenv["DATABASE_KEY"].toString()
        )

        transaction (database) {
            SchemaUtils.create(Users)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
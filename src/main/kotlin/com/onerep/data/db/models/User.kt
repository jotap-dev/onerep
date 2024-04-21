package com.onerep.data.db.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
)

@Serializable
data class UserWithPassword(
    val id: Int,
    val name: String,
    val email: String,
    val password: String
)

@Serializable
data class UserCredentials(
    val email: String,
    val password: String
)

@Serializable
data class UserData(
    val name: String,
    val email: String,
    val password: String
)

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 128)
    val email = varchar("email", 128)
    val password = varchar("password", 128)

    override val primaryKey = PrimaryKey(id)
}

package com.onerep.data.db.dao

import com.onerep.data.db.DatabaseFactory.dbQuery
import com.onerep.data.db.models.User
import com.onerep.data.db.models.UserWithPassword
import com.onerep.data.db.models.Users
import com.onerep.data.db.models.Users.id
import com.onerep.data.db.models.Users.name
import com.onerep.data.db.models.Users.email
import com.onerep.data.db.models.Users.password
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class UsersDao : IUsersDao {

    private fun resultRowToUser(row : ResultRow) = User(
        id = row[id],
        name = row[name],
        email = row[email]
    )

    private fun resultRowToUserWithPassword(row : ResultRow) = UserWithPassword(
            id = row[id],
            name = row[name],
            email = row[email],
            password = row[password]
        )

    override suspend fun allUsers(): List<User> = dbQuery{
        Users.selectAll().map(::resultRowToUser)
    }

    override suspend fun user(id: Int): User? = dbQuery {
        Users.select { Users.id eq id }
            .map(::resultRowToUser)
            .singleOrNull()
    }

    override suspend fun userByEmail(email: String): UserWithPassword? = dbQuery{
        Users.select { Users.email eq email }
            .map(::resultRowToUserWithPassword)
            .singleOrNull()
    }

    override suspend fun userWithPassword(id: Int): UserWithPassword? = dbQuery {
        Users.select { Users.id eq id }
            .map(::resultRowToUserWithPassword)
            .singleOrNull()
    }

    override suspend fun addNewUser(
        name: String,
        email: String,
        password: String
    ): UserWithPassword?  = dbQuery{
        val insertedUser = Users.insert {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.password] = password
        }
        insertedUser.resultedValues?.singleOrNull()?.let(::resultRowToUserWithPassword)
    }

    override suspend fun editUser(
        id: Int,
        name: String?,
        email: String?
    ): Boolean = dbQuery{
        Users.update({ Users.id eq id }) {update ->
            name?.let { update[Users.name] = name }
            email?.let { update[Users.email] = email }
        } > 0
    }

    override suspend fun editPassword(
        id: Int,
        newPassword: String
    ): Boolean  = dbQuery{
        Users.update({ Users.id eq id }) {
            it[password] = newPassword }
        } > 0

    override suspend fun deleteUser(id: Int): Boolean = dbQuery {
        Users.deleteWhere { Users.id eq id } > 0
    }
}
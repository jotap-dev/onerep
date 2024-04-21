package com.onerep.data.db.dao

import com.onerep.data.db.models.User
import com.onerep.data.db.models.UserWithPassword

interface IUsersDao {

    suspend fun allUsers(): List<User>

    suspend fun user(id: Int): User?

    suspend fun userByEmail(email: String): UserWithPassword?

    suspend fun userWithPassword(id: Int): UserWithPassword?

    suspend fun addNewUser(
        name: String,
        email: String,
        password: String
    ): UserWithPassword?

    suspend fun editUser(
        id: Int,
        name: String?,
        email: String?
    ): Boolean

    suspend fun editPassword(id: Int, newPassword: String): Boolean

    suspend fun deleteUser(id: Int): Boolean
}
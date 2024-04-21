package com.onerep.data.db.repositories.users

import com.onerep.data.db.dao.IUsersDao
import com.onerep.data.db.dao.UsersDao
import com.onerep.data.db.models.User
import com.onerep.data.db.models.UserWithPassword

class UsersRepository(private val usersDao: UsersDao) : IUsersRepository {
    override suspend fun allUsers(): List<User> = usersDao.allUsers()

    override suspend fun user(id: Int): User? = usersDao.user(id)
    override suspend fun userByEmail(email: String): UserWithPassword? = usersDao.userByEmail(email)

    override suspend fun userWithPassword(id: Int): UserWithPassword? = usersDao.userWithPassword(id)

    override suspend fun addNewUser(
        name: String,
        email: String,
        password: String
    ): UserWithPassword? = usersDao.addNewUser(name, email, password)

    override suspend fun editUser(
        id: Int,
        name: String?,
        email: String?
    ): Boolean = usersDao.editUser(id, name, email)

    override suspend fun editPassword(
        id: Int,
        newPassword: String
    ): Boolean = usersDao.editPassword(id, newPassword)

    override suspend fun deleteUser(id: Int): Boolean = usersDao.deleteUser(id)
}
package com.onerep

import com.onerep.data.db.DatabaseFactory
import com.onerep.data.db.dao.IUsersDao
import com.onerep.data.db.dao.UsersDao
import com.onerep.plugins.*
import com.onerep.routes.workouts.workouts
import io.github.cdimascio.dotenv.dotenv
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    val dao: UsersDao = UsersDao()
    DatabaseFactory.init()

    configureSerialization()
    configureDatabases()
    configureSecurity()
    configureRouting(dao)
    workouts()
}

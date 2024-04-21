package com.onerep.routes.workouts

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.workouts(){
    routing {
        authenticate("auth-jwt"){
            get("/workout"){
                call.respond(HttpStatusCode.OK, "Seu Token JWT é válido e não está expirado!")
            }
        }
    }
}
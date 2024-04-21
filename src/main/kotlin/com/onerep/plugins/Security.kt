package com.onerep.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureSecurity() {

    val dotenv = dotenv()
    // Please read the jwt property from the config file if you are using EngineMain
    val jwtAudience = dotenv["AUDIENCE"].toString()
    val jwtIssuer = dotenv["DOMAIN"].toString()
    val jwtRealm = dotenv["REALM"].toString()
    val jwtSecret = dotenv["JWT_SECRET"].toString()
    authentication {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                JWT
                    .require(Algorithm.HMAC256(jwtSecret))
                    .withAudience(jwtAudience)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("name").asString() != "") JWTPrincipal(credential.payload) else null
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token inválido ou expirado")
            }
        }
    }


}

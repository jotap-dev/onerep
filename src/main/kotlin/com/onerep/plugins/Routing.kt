package com.onerep.plugins

import at.favre.lib.crypto.bcrypt.BCrypt
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.onerep.data.db.dao.UsersDao
import com.onerep.data.db.models.UserCredentials
import com.onerep.data.db.models.UserData
import com.onerep.data.db.models.UserWithPassword
import com.onerep.data.db.repositories.users.UsersRepository
import io.github.cdimascio.dotenv.dotenv
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Application.configureRouting(usersDao: UsersDao) {

    val dotenv = dotenv()

    val secret = dotenv["JWT_SECRET"].toString()
    val issuer = dotenv["DOMAIN"].toString()
    val audience = dotenv["AUDIENCE"].toString()

    fun generateToken(userWithPassword: UserWithPassword?): HashMap<String, String> {

        val expirationTimeMillis = System.currentTimeMillis() + 3 * 60 * 60 * 1000

        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", userWithPassword?.name)
            .withExpiresAt(Date(expirationTimeMillis))
            .sign(Algorithm.HMAC256(secret))
        return hashMapOf("token" to token)
    }

    val usersRepository = UsersRepository(usersDao)

    routing {
        route("/user"){
            get("/"){
                call.respondText("Seja Bem vindo, você está no /user")
            }

            post("/add"){
                val user = call.receive<UserData>()

                val password = BCrypt.withDefaults().hashToString(12, user.password.toCharArray())

                usersRepository.addNewUser(user.name,user.email, password)
                call.respond(status = HttpStatusCode.Created, "Success on create your account")
            }
            post("/login"){
                val credentials = call.receive<UserCredentials>()
                val user = usersRepository.userByEmail(credentials.email)

                val isPasswordEquals: Boolean =
                    BCrypt.verifyer()
                        .verify(credentials.password.toCharArray(), user?.password)
                        .verified

                if (isPasswordEquals){
                    val token = generateToken(user)
                    call.respond(status = HttpStatusCode.Accepted, token)
                }else{
                    call.respond(status = HttpStatusCode.BadRequest, "Senha inválida")
                }
            }

            authenticate("auth-jwt"){
                get("/all"){
                    call.respond(usersRepository.allUsers())
                }
            }
        }

    }


}

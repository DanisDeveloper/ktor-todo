package danis.galimullin

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import danis.galimullin.presentation.config.JwtConfig
import danis.galimullin.presentation.route.auth.authRouting
import presentation.route.task.taskRouting
import danis.galimullin.presentation.route.user.userRouting
import data.db.DatabaseInitializer
import infrastructure.di.diModule
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // Инициализация базы
    DatabaseInitializer.init()

    // DI через Koin
    installKoin()

    // Конфигурация плагинов
    installStatusPages()
    installContentNegotiation()
    installAuthentication()

    // Роуты
    installRouting()
}

// ------------------ Extensions ------------------

private fun Application.installKoin() {
    install(Koin) {
        slf4jLogger()
        modules(diModule)
    }
}

private fun Application.installStatusPages() {
    install(StatusPages) {
        exception<IllegalArgumentException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, mapOf("error" to (cause.message ?: "Invalid request")))
        }
        exception<Throwable> { call, cause ->
            call.respond(HttpStatusCode.InternalServerError, mapOf("error" to (cause.message ?: "Server Error")))
        }
    }
}

private fun Application.installContentNegotiation() {
    install(ContentNegotiation) {
        json()
    }
}

private fun Application.installAuthentication() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = JwtConfig.realm
            verifier(
                JWT.require(Algorithm.HMAC256(JwtConfig.secret))
                    .withAudience(JwtConfig.audience)
                    .withIssuer(JwtConfig.issuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(JwtConfig.audience)) JWTPrincipal(credential.payload)
                else null
            }
        }
    }
}

private fun Application.installRouting() {
    routing {
        authRouting()
        userRouting()
        taskRouting()
    }
}

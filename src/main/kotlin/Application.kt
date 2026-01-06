package danis.galimullin

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import danis.galimullin.domain.exception.ForbiddenException
import danis.galimullin.presentation.config.JwtConfig
import danis.galimullin.presentation.route.auth.authRouting
import presentation.route.task.taskRouting
import danis.galimullin.presentation.route.user.userRouting
import data.db.DatabaseInitializer
import infrastructure.di.appModule
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.calllogging.CallLogging
import io.ktor.server.plugins.calllogging.processingTimeMillis
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.httpMethod
import io.ktor.server.request.uri
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.slf4j.event.Level

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    // Инициализация базы
    DatabaseInitializer.init()

    install(CallLogging) {
        level = Level.INFO

        format { call ->
            val status = call.response.status()
            val httpMethod = call.request.httpMethod.value
            val uri = call.request.uri
            val duration = call.processingTimeMillis()
            "Status: $status, HTTP: $httpMethod, URI: $uri, Duration: ${duration}ms"
        }
    }

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
        modules(appModule)
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
        exception<ForbiddenException> { call, _ ->
            call.respond(HttpStatusCode.Forbidden)
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

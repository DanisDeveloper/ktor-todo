package danis.galimullin.presentation.route.user

import danis.galimullin.domain.usecase.user.GetUserByIdUseCase
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.userRouting() {
    val getUserByIdUseCase by inject<GetUserByIdUseCase>()
    authenticate("auth-jwt") {

        route("/users") {
            get {
                val user = getUserByIdUseCase(call.userId())
                call.respond(user)
            }
        }
    }
}


fun ApplicationCall.userId(): Long =
    principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asLong() ?: throw IllegalArgumentException("Unauthorized")

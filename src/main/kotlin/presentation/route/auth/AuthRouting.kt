package danis.galimullin.presentation.route.auth

import danis.galimullin.domain.usecase.auth.LoginUserUseCase
import danis.galimullin.domain.usecase.auth.RegisterUserUseCase
import danis.galimullin.presentation.route.auth.dto.AuthDTO
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Route.authRouting() {
    val registerUserUseCase by inject<RegisterUserUseCase>()
    val loginUserUseCase by inject<LoginUserUseCase>()
    route("/auth") {
        post("/register") {
            val authUserDTO = call.receive<AuthDTO>()
            val user = registerUserUseCase(authUserDTO.email, authUserDTO.password)
            call.respond(user)
        }
        post("/login") {
            val authUserDTO = call.receive<AuthDTO>()
            val token = loginUserUseCase(authUserDTO.email, authUserDTO.password)
            call.respond(mapOf("token" to token))
        }
    }
}
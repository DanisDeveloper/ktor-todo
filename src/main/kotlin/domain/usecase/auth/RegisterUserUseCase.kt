package danis.galimullin.domain.usecase.auth

import danis.galimullin.User
import danis.galimullin.domain.repository.UserRepository
import infrastructure.security.api.PasswordHasher
import danis.galimullin.domain.usecase.exceptions.UserAlreadyExists
import infrastructure.security.api.TokenService

class RegisterUserUseCase(
    private val repository: UserRepository,
    private val passwordHasher: PasswordHasher,
    private val tokenService: TokenService
) {
    suspend operator fun invoke(email: String, password: String): String {
        if (repository.existsByEmail(email)) {
            throw UserAlreadyExists()
        }

        val hashedPassword = passwordHasher.hash(password)
        val registeredUser = repository.register(email, hashedPassword)
        return tokenService.generateToken(registeredUser.id)
    }
}
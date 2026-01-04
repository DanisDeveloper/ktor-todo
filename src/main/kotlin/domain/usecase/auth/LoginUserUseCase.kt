package danis.galimullin.domain.usecase.auth

import danis.galimullin.domain.repository.UserRepository
import infrastructure.security.api.PasswordHasher
import infrastructure.security.api.TokenService
import danis.galimullin.domain.usecase.exceptions.InvalidCredentialsException
import danis.galimullin.domain.usecase.exceptions.UserNotFoundException

class LoginUserUseCase(
    private val repository: UserRepository,
    private val passwordHasher: PasswordHasher,
    private val tokenService: TokenService
) {
    suspend operator fun invoke(email: String, password: String): String {
        val user = repository.findByEmail(email) ?: throw UserNotFoundException()

        if (!passwordHasher.verify(password, user.hashedPassword)) {
            throw InvalidCredentialsException()
        }

        return tokenService.generateToken(user.id)
    }
}
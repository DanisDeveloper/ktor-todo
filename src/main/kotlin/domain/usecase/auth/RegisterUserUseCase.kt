package danis.galimullin.domain.usecase.auth

import danis.galimullin.User
import danis.galimullin.domain.repository.UserRepository
import infrastructure.security.api.PasswordHasher
import danis.galimullin.domain.usecase.exceptions.UserAlreadyExists

class RegisterUserUseCase(
    private val repository: UserRepository,
    private val passwordHasher: PasswordHasher
) {
    suspend operator fun invoke(email: String, password: String): User{
        if(repository.existsByEmail(email)){
            throw UserAlreadyExists()
        }

        val hashedPassword = passwordHasher.hash(password)
        return repository.register(email, hashedPassword)
    }
}
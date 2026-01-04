package danis.galimullin.domain.usecase.user

import danis.galimullin.User
import danis.galimullin.domain.repository.UserRepository
import danis.galimullin.domain.usecase.exceptions.UserNotFoundException

class GetUserByIdUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id: Long): User = repository.getById(id) ?: throw UserNotFoundException()
}
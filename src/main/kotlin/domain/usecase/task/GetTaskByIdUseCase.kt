package danis.galimullin.domain.usecase.task

import domain.repository.TaskRepository
import io.ktor.server.plugins.NotFoundException

class GetTaskByIdUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: Long) = repository.getById(taskId) ?: throw NotFoundException()
}
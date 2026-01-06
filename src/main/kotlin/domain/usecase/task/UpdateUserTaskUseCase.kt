package domain.usecase.task

import danis.galimullin.domain.exception.ForbiddenException
import domain.repository.TaskRepository
import io.ktor.server.plugins.NotFoundException

class UpdateUserTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(
        taskId: Long,
        title: String,
        userId: Long,
    ) {
        val task = repository.getById(taskId) ?: throw NotFoundException()

        if(userId != task.userId) {
            throw ForbiddenException()
        }
        val updatedTask = task.copy(
            title = title,
        )

        repository.update(updatedTask)
    }
}
package domain.usecase.task

import danis.galimullin.Task
import danis.galimullin.domain.exception.ForbiddenException
import domain.repository.TaskRepository
import io.ktor.server.plugins.NotFoundException

class GetUserTaskByIdUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: Long, userId: Long): Task {
        val task = repository.getById(taskId) ?: throw NotFoundException()

        if(userId != task.userId) {
            throw ForbiddenException()
        }

        return task
    }
}
package domain.usecase.task

import domain.repository.TaskRepository
import io.ktor.server.plugins.NotFoundException

class UpdateUserTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(
        taskId: Long,
        title: String,
    ) {
        val task = repository.getById(taskId) ?: throw NotFoundException()

        val updatedTask = task.copy(
            title = title,
        )

        repository.update(updatedTask)
    }
}
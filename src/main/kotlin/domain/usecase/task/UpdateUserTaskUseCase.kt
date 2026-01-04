package domain.usecase.task

import domain.repository.TaskRepository
import io.ktor.server.plugins.NotFoundException

class UpdateUserTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(
        taskId: Long,
        title: String?,
        isCompleted: Boolean?
    ) {
        val task = repository.getById(taskId) ?: throw NotFoundException()

        val updatedTask = task.copy(
            title = title ?: task.title,
            isCompleted = isCompleted ?: task.isCompleted
        )

        repository.update(updatedTask)
    }
}
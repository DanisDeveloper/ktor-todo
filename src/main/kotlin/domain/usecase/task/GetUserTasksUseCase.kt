package domain.usecase.task

import danis.galimullin.Task
import domain.repository.TaskRepository

class GetUserTasksUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(userId: Long): List<Task> = repository.getAllByUserId(userId)
}
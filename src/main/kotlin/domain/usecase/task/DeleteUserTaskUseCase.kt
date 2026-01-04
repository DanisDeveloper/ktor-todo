package danis.galimullin.domain.usecase.task

import domain.repository.TaskRepository

class DeleteUserTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(taskId: Long) {
        repository.delete(taskId)
    }
}
package danis.galimullin.domain.usecase.task

import domain.repository.TaskRepository

class ToggleTaskUseCase(private val repository: TaskRepository)  {
    suspend operator fun invoke(taskId: Long) {
        repository.toggle(taskId)
    }
}
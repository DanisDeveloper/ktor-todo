package danis.galimullin.domain.usecase.task

import danis.galimullin.Task
import domain.repository.TaskRepository

class CreateTaskUseCase(private val repository: TaskRepository) {
    suspend operator fun invoke(title: String, userId: Long): Task {
        return repository.create(
            Task(
                id = 0,
                title = title,
                isCompleted = false,
                userId = userId,
            )
        )
    }
}
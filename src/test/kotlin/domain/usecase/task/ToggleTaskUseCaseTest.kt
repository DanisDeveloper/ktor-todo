package domain.usecase.task

import danis.galimullin.Task
import domain.repository.TaskRepository
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class ToggleTaskUseCaseTest : StringSpec({
    val repository = mockk<TaskRepository>(relaxed = true)
    val useCase = ToggleTaskUseCase(repository)

    "should toggle task" {
        val taskId = 1L
        val userId = 1L
        val title = "title"
        val isCompleted = false
        val task = Task(taskId, title, isCompleted, userId)

        coEvery { repository.getById(taskId) } returns task
        coEvery { repository.toggle(any()) } returns Unit

        useCase(taskId, userId)

        coVerify(exactly = 1) {
            repository.toggle(taskId)
        }
    }
})
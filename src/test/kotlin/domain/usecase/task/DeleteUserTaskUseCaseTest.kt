package domain.usecase.task

import danis.galimullin.Task
import domain.repository.TaskRepository
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class DeleteUserTaskUseCaseTest : StringSpec({
    val repository = mockk<TaskRepository>()
    val useCase = DeleteUserTaskUseCase(repository)
    "should delete user task" {
        val taskId = 1L
        val userId = 1L
        val task = Task(
            id = taskId,
            title = "test",
            userId = userId,
            isCompleted = false
        )

        coEvery { repository.getById(taskId) } returns task
        coEvery { repository.delete(any()) } returns Unit

        useCase(taskId, userId)

        coVerify(exactly = 1) { repository.delete(taskId) }
    }
})
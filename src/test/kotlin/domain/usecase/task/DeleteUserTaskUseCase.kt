package domain.usecase.task

import danis.galimullin.domain.usecase.task.DeleteUserTaskUseCase
import domain.repository.TaskRepository
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk

class DeleteUserTaskUseCase : StringSpec({
    val repository = mockk<TaskRepository>()
    val useCase = DeleteUserTaskUseCase(repository)
    "should delete user task" {
        val taskId = 1L

        coEvery { repository.delete(any()) } returns Unit

        useCase(taskId)

        coVerify(exactly = 1) { repository.delete(taskId) }

    }
})
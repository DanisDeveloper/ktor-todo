package domain.usecase.task

import danis.galimullin.Task
import domain.repository.TaskRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.server.plugins.*
import io.mockk.coEvery
import io.mockk.mockk

class GetTaskByIdUseCaseTest : StringSpec({
    val repository = mockk<TaskRepository>()
    val useCase = GetUserTaskByIdUseCase(repository)

    "should return a success result when getting task by id" {
        val title = "new task"
        val userId = 1L
        val taskId = 1L
        val task = Task(taskId, title, false, userId)
        coEvery { repository.getById(taskId) } returns task

        val result = useCase(taskId, userId)

        result shouldBe task
    }

    "should throw an exception if not existing task" {
        val taskId = 2L
        val userId = 1L
        coEvery { repository.getById(taskId) } returns null
        shouldThrow<NotFoundException> {
            useCase(taskId, userId)
        }
    }
})
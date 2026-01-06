package domain.usecase.task

import danis.galimullin.Task
import danis.galimullin.domain.usecase.task.GetUserTaskByIdUseCase
import domain.repository.TaskRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.server.plugins.*
import io.mockk.coEvery
import io.mockk.mockk

class GetTaskByIdUseCase : StringSpec({
    val repository = mockk<TaskRepository>()
    val useCase = GetUserTaskByIdUseCase(repository)

    "should return a success result when getting task by id" {
        val title = "new task"
        val userId = 1L
        val task = Task(1, title, false, userId)
        coEvery { repository.getById(1) } returns task

        val result = useCase(1)

        result shouldBe task
    }

    "should throw an exception if not existing task" {
        coEvery { repository.getById(2) } returns null
        shouldThrow<NotFoundException> {
            useCase(2)
        }
    }
})
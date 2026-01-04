package domain.usecase.task

import danis.galimullin.Task
import danis.galimullin.domain.usecase.task.CreateTaskUseCase
import domain.repository.TaskRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.ktor.server.plugins.NotFoundException
import io.mockk.coEvery
import io.mockk.mockk

class CreateTaskUseCase : StringSpec({
    val repository = mockk<TaskRepository>()
    val useCase = CreateTaskUseCase(repository)

    "should create a new task" {
        val title = "new task"
        val userId = 1L
        val task = Task(1, title, false, userId)
        coEvery { useCase(title, userId) } returns task

        val result = useCase(title, userId)

        result shouldBe task
    }


})
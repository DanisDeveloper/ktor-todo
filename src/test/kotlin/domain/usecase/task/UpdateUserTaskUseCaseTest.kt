package domain.usecase.task

import danis.galimullin.Task
import domain.repository.TaskRepository
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.ktor.server.plugins.NotFoundException

class UpdateUserTaskUseCaseTest : StringSpec({

    val repository = mockk<TaskRepository>(relaxed = true)
    val useCase = UpdateUserTaskUseCase(repository)

    "should update existing task" {
        val taskId = 1L
        val userId = 1L
        val oldTitle = "Old Title"
        val originalTask = Task(taskId, oldTitle, false, userId)
        val updatedTitle = "New title"

        coEvery { repository.getById(taskId) } returns originalTask
        coEvery { repository.update(any()) } returns Unit

        useCase(taskId, updatedTitle, userId)

        coVerify {
            repository.update(match {
                it.id == taskId && it.title == updatedTitle && it.userId == userId
            })
        }
    }


    "should throw NotFoundException if task does not exist" {
        val taskId = 99L
        val userId = 1L
        val title = "Any title"
        coEvery { repository.getById(taskId) } returns null

        shouldThrow<NotFoundException> {
            useCase(taskId, title, userId)
        }
    }
})

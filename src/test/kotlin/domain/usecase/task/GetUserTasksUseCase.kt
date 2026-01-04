package domain.usecase.task

import danis.galimullin.Task
import domain.repository.TaskRepository
import domain.usecase.GetUserTasksUseCase
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk

class GetUserTasksUseCase : StringSpec({
    val repository = mockk<TaskRepository>()
    val useCase = GetUserTasksUseCase(repository)

    "should get all user's tasks" {
        val userId = 1L
        val tasks = listOf(
            Task(1L, "task1", false, userId),
            Task(2L, "task2", false, userId),
            Task(3L, "task3", false, userId),
        )

        coEvery { repository.getAllByUserId(userId) } returns tasks

        val result = useCase(userId)

        result shouldBe tasks

        coVerify(exactly = 1) { repository.getAllByUserId(userId) }
    }

    "should return empty list if user has no tasks" {
        val userId = 2L

        coEvery { repository.getAllByUserId(userId) } returns emptyList()

        val result = useCase(userId)

        result.shouldContainExactly(emptyList())
    }
})
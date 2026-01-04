package domain.repository

import danis.galimullin.Task

interface TaskRepository {
    suspend fun getAllByUserId(userId: Long): List<Task>
    suspend fun getById(taskId: Long): Task?
    suspend fun create(task: Task): Task
    suspend fun update(task: Task)
    suspend fun delete(taskId: Long)
}

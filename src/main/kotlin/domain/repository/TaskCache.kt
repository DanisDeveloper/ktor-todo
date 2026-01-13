package domain.repository

import danis.galimullin.Task

interface TaskCache {

    suspend fun getUserTasks(userId: Long): List<Task>?

    suspend fun putUserTasks(userId: Long, tasks: List<Task>)

    suspend fun removeUserTasks(userId: Long)

    suspend fun getTask(taskId: Long): Task?

    suspend fun putTask(task: Task)

    suspend fun removeTask(taskId: Long)
}
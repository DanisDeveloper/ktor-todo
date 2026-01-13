@file:OptIn(ExperimentalLettuceCoroutinesApi::class)

package infrastructure.database.redis

import danis.galimullin.Task
import domain.repository.TaskCache
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import kotlinx.serialization.json.Json

class RedisTaskCache(
    val redisManager: RedisManager,
) : TaskCache {
    override suspend fun getUserTasks(userId: Long): List<Task>? =
        redisManager.commands.get("tasks:user:$userId")?.let { Json.decodeFromString(it) }

    override suspend fun putUserTasks(userId: Long, tasks: List<Task>) {
        redisManager.commands.setex("tasks:user:$userId", 60, Json.encodeToString(tasks))
    }

    override suspend fun removeUserTasks(userId: Long) {
        redisManager.commands.del("tasks:user:$userId")
    }

    override suspend fun getTask(taskId: Long): Task? =
        redisManager.commands.get("task:$taskId")?.let { Json.decodeFromString(it) }

    override suspend fun putTask(task: Task) {
        redisManager.commands.setex("task:${task.id}", 60, Json.encodeToString(task))
    }

    override suspend fun removeTask(taskId: Long) {
        redisManager.commands.del("task:$taskId")
    }
}
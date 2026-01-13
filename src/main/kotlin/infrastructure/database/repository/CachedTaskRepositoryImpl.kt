package infrastructure.database.repository

import danis.galimullin.Task
import domain.repository.TaskCache
import domain.repository.TaskRepository

class CachedTaskRepositoryImpl constructor(
    private val taskRepository: TaskRepository,
    private val taskCache: TaskCache,
) : TaskRepository {
    override suspend fun getAllByUserId(userId: Long): List<Task> {
        taskCache.getUserTasks(userId)?.let { return it }
        val tasks = taskRepository.getAllByUserId(userId)
        taskCache.putUserTasks(userId, tasks)
        return tasks
    }

    override suspend fun getById(taskId: Long): Task? {
        taskCache.getTask(taskId)?.let { return it }
        val task = taskRepository.getById(taskId)
        task?.let { taskCache.putTask(it) }
        return task
    }

    override suspend fun create(task: Task): Task {
        taskCache.removeUserTasks(task.userId)
        return taskRepository.create(task)
    }

    override suspend fun update(task: Task) {
        taskCache.removeTask(task.id)
        taskCache.removeUserTasks(task.userId)
        taskRepository.update(task)
    }

    override suspend fun toggle(taskId: Long) {
        val task = taskRepository.getById(taskId) ?: return
        taskRepository.toggle(taskId)
        taskCache.removeTask(taskId)
        taskCache.removeUserTasks(task.userId)
    }

    override suspend fun delete(taskId: Long) {
        val task = taskRepository.getById(taskId) ?: return
        taskRepository.delete(taskId)
        taskCache.removeTask(taskId)
        taskCache.removeUserTasks(task.userId)
    }
}
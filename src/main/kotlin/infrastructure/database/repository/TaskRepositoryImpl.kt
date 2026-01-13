package danis.galimullin.infrastructure.database.repository

import danis.galimullin.Task
import data.db.table.TasksTable
import domain.repository.TaskRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class TaskRepositoryImpl : TaskRepository {
    override suspend fun getAllByUserId(userId: Long): List<Task> =
        transaction {
            TasksTable
                .select { TasksTable.userId eq userId }
                .map {
                    Task(
                        id = it[TasksTable.id],
                        title = it[TasksTable.title],
                        isCompleted = it[TasksTable.isCompleted],
                        userId = it[TasksTable.userId]
                    )
                }
        }

    override suspend fun getById(taskId: Long): Task? = transaction {
        TasksTable.select { TasksTable.id eq taskId }
            .map {
                Task(
                    id = it[TasksTable.id],
                    title = it[TasksTable.title],
                    isCompleted = it[TasksTable.isCompleted],
                    userId = it[TasksTable.userId]
                )
            }
            .singleOrNull()
    }


    override suspend fun create(task: Task): Task {
        val id = transaction {
            TasksTable.insert {
                it[title] = task.title
                it[isCompleted] = task.isCompleted
                it[userId] = task.userId
            } get TasksTable.id
        }
        return task.copy(id = id)
    }


    override suspend fun update(task: Task) {
        transaction {
            TasksTable.update({ TasksTable.id eq task.id }) {
                it[title] = task.title
                it[isCompleted] = task.isCompleted
                it[userId] = task.userId
            }
        }
    }

    override suspend fun toggle(taskId: Long) {
        transaction {
            val current = TasksTable
                .slice(TasksTable.isCompleted)
                .select { TasksTable.id eq taskId }
                .singleOrNull()?.get(TasksTable.isCompleted)
                ?: throw IllegalArgumentException("Task not found")

            TasksTable.update({ TasksTable.id eq taskId }) {
                it[isCompleted] = !current
            }
        }
    }

    override suspend fun delete(taskId: Long) {
        transaction {
            TasksTable.deleteWhere { TasksTable.id eq taskId }
        }
    }
}
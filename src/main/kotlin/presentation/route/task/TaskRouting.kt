package presentation.route.task

import danis.galimullin.domain.usecase.task.CreateTaskUseCase
import danis.galimullin.domain.usecase.task.DeleteUserTaskUseCase
import domain.usecase.task.UpdateUserTaskUseCase
import danis.galimullin.presentation.route.task.dto.CreateTaskDTO
import danis.galimullin.presentation.route.task.dto.UpdateTaskDTO
import domain.usecase.GetUserTasksUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.taskRouting() {
    val getUserTasksUseCase by inject<GetUserTasksUseCase>()
    val createTaskUseCase by inject<CreateTaskUseCase>()
    val updateUserTaskUseCase by inject<UpdateUserTaskUseCase>()
    val deleteUserTaskUseCase by inject<DeleteUserTaskUseCase>()
    authenticate("auth-jwt") {
        route("/tasks") {
            get {
                val tasks = getUserTasksUseCase(call.userId())
                call.respond(tasks)
            }

            post {
                val taskDto = call.receive<CreateTaskDTO>()
                val createdTask = createTaskUseCase(taskDto.title, call.userId())
                call.respond(createdTask)
            }

            put("/{task_id}") {
                val taskDto = call.receive<UpdateTaskDTO>()
                updateUserTaskUseCase(call.taskId(), taskDto.title, taskDto.is_completed)
                call.respond(HttpStatusCode.OK, mapOf("status" to "updated"))
            }

            delete("/{task_id}") {
                deleteUserTaskUseCase(call.taskId())
                call.respond(HttpStatusCode.OK, mapOf("status" to "deleted"))
            }
        }
    }
}

fun ApplicationCall.userId(): Long =
    principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asLong() ?: throw IllegalArgumentException("Unauthorized")


fun ApplicationCall.taskId(): Long = parameters["task_id"]?.toLongOrNull()
    ?: throw IllegalArgumentException("task_id is required and must be an integer")
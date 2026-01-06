package presentation.route.task

import danis.galimullin.domain.usecase.task.CreateTaskUseCase
import danis.galimullin.domain.usecase.task.DeleteUserTaskUseCase
import danis.galimullin.domain.usecase.task.GetUserTaskByIdUseCase
import danis.galimullin.domain.usecase.task.ToggleTaskUseCase
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
    val getUserTaskByIdUseCase by inject<GetUserTaskByIdUseCase>()
    val createTaskUseCase by inject<CreateTaskUseCase>()
    val updateUserTaskUseCase by inject<UpdateUserTaskUseCase>()
    val toggleTaskUseCase by inject<ToggleTaskUseCase>()
    val deleteUserTaskUseCase by inject<DeleteUserTaskUseCase>()

    authenticate("auth-jwt") {
        route("/tasks") {
            get {
                val tasks = getUserTasksUseCase(call.userId())
                call.respond(tasks)
            }
            get("/tasks/{id}") {
                val task = getUserTaskByIdUseCase(call.taskId())
                call.respond(task)
            }

            post {
                val taskDto = call.receive<CreateTaskDTO>()
                val createdTask = createTaskUseCase(taskDto.title, call.userId())
                call.respond(createdTask)
            }

            put("/{task_id}") {
                // TODO сделать проверку, что автор задачи - текущий пользователь
                val taskDto = call.receive<UpdateTaskDTO>()
                updateUserTaskUseCase(call.taskId(), taskDto.title)
                call.respond(HttpStatusCode.OK, mapOf("status" to "updated"))
            }

            patch("/{task_id}/toggle") {
                toggleTaskUseCase(call.taskId())
                call.respond(HttpStatusCode.OK, mapOf("status" to "toggled"))
            }

            delete("/{task_id}") {
                // TODO сделать проверку, что автор задачи - текущий пользователь
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
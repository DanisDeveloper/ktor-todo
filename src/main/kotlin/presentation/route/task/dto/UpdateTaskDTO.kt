package danis.galimullin.presentation.route.task.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateTaskDTO (
    val title: String
)
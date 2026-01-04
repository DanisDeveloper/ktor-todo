package danis.galimullin

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: Long,
    val title: String,
    val isCompleted: Boolean = false,
    val userId: Long
)


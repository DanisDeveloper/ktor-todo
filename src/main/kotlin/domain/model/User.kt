package danis.galimullin

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long,
    val email: String,
    val hashedPassword: String,
)
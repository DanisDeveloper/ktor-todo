package danis.galimullin.presentation.route.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthDTO(
    val email: String,
    val password: String
)

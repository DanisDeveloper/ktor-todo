package danis.galimullin.domain.repository

import danis.galimullin.User

interface UserRepository {
    suspend fun register(email: String, hashedPassword: String): User
    suspend fun findByEmail(email: String): User?
    suspend fun existsByEmail(email: String): Boolean
    suspend fun getById(id: Long): User?
}
package infrastructure.security.api

interface TokenService {
    fun generateToken(userId: Long): String
    fun verifyToken(token: String): Long?
}
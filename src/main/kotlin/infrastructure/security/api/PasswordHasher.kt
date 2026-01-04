package infrastructure.security.api

interface PasswordHasher {
    fun hash(password: String): String
    fun verify(password: String, hashedPassword: String): Boolean
}

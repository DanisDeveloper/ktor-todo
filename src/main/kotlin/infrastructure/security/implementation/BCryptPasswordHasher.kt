package infrastructure.security.implementation

import infrastructure.security.api.PasswordHasher
import org.mindrot.jbcrypt.BCrypt

class BCryptPasswordHasher: PasswordHasher {
    override fun hash(password: String): String = BCrypt.hashpw(password, BCrypt.gensalt())

    override fun verify(password: String, hashedPassword: String): Boolean = BCrypt.checkpw(password, hashedPassword)
}
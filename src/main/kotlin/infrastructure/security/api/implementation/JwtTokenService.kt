package infrastructure.security.implementation

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import infrastructure.security.api.TokenService
import danis.galimullin.infrastructure.security.JwtConfig
import java.util.Date

class JwtTokenService : TokenService {
    private val algorithm = Algorithm.HMAC256(JwtConfig.secret)
    override fun generateToken(userId: Long): String =
        JWT.create()
            .withIssuer(JwtConfig.issuer)
            .withAudience(JwtConfig.audience)
            .withClaim("userId", userId)
            .withExpiresAt(Date(System.currentTimeMillis() + JwtConfig.expirationMs))
            .sign(algorithm)

    override fun verifyToken(token: String): Long? {
        val verifier = JWT.require(algorithm).build()
        return verifier.verify(token).getClaim("userId").asLong()
    }
}
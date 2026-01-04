package danis.galimullin.presentation.config

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig

object JwtConfig {
    private val config = HoconApplicationConfig(ConfigFactory.load()).config("ktor.jwt")

    val secret: String = config.property("secret").getString()
    val issuer: String = config.property("issuer").getString()
    val realm: String = config.property("realm").getString()
    val audience: String = config.property("audience").getString()
    val expirationMs: Long = config.property("expirationMs").getString().toLong()
}
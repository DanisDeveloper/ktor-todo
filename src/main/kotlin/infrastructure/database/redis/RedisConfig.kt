package danis.galimullin.infrastructure.database.redis

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig

object RedisConfig {
    private val config = HoconApplicationConfig(ConfigFactory.load()).config("ktor.redis")

    val url: String = config.property("url").getString()
}
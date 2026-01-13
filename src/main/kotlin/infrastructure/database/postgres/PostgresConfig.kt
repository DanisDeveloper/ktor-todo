package danis.galimullin.infrastructure.data.database

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig

object PostgresConfig {
    private val config = HoconApplicationConfig(ConfigFactory.load()).config("ktor.database")

    val url = config.property("url").getString()
    val driver = config.property("driver").getString()
    val user = config.property("user").getString()
    val password = config.property("password").getString()
}
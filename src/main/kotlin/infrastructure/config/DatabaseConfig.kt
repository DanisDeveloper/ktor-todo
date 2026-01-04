package danis.galimullin.presentation.config

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig

object DatabaseConfig {
    private val config = HoconApplicationConfig(ConfigFactory.load()).config("ktor.database")

    val url = config.property("url").getString()
    val driver = config.property("driver").getString()
    val user = config.property("user").getString()
    val password = config.property("password").getString()
}
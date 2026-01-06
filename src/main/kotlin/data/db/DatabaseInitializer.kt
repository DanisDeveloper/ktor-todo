package data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import danis.galimullin.presentation.config.DatabaseConfig
import liquibase.command.CommandScope
import liquibase.command.core.UpdateCommandStep
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.jetbrains.exposed.sql.Database
import javax.sql.DataSource

object DatabaseInitializer {

    fun createDataSource(): HikariDataSource {
        val config = HikariConfig().apply {
            jdbcUrl = DatabaseConfig.url
            driverClassName = DatabaseConfig.driver
            username = DatabaseConfig.user
            password = DatabaseConfig.password
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

    fun migrate(dataSource: DataSource) {
        dataSource.connection.use { connection ->
            val database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(JdbcConnection(connection))

            CommandScope(*UpdateCommandStep.COMMAND_NAME).apply {
                addArgumentValue("changelogFile", "db/changelog/master.yaml")
                addArgumentValue("database", database)
                addArgumentValue("resourceAccessor", ClassLoaderResourceAccessor())

                execute()
            }
        }
    }

    fun init() {
        val dataSource = createDataSource()
        migrate(dataSource)
        Database.connect(dataSource)

    }
}
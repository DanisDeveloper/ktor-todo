package data.db

import danis.galimullin.presentation.config.DatabaseConfig
import data.db.table.TasksTable
import data.db.table.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = DatabaseConfig.url,
            driver = DatabaseConfig.driver,
            user = DatabaseConfig.user,
            password = DatabaseConfig.password
        )

        transaction {
            SchemaUtils.create(UsersTable, TasksTable)
        }
    }
}
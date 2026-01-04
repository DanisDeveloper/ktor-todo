package data.db.table

import org.jetbrains.exposed.sql.Table

object TasksTable : Table("tasks") {
    val id = long("id").autoIncrement()
    val title = varchar("title", 255)
    val isCompleted = bool("is_completed").default(false)

    val userId = long("user_id").references(UsersTable.id)
    override val primaryKey = PrimaryKey(id)
}


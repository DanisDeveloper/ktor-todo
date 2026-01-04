package data.db.table

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {
    val id = long("id").autoIncrement()
    val email = varchar("email", 255)
    val hashedPassword = varchar("hashed_password", 255)

    override val primaryKey = PrimaryKey(id)
}
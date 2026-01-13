package danis.galimullin.infrastructure.database.repository

import danis.galimullin.User
import danis.galimullin.domain.repository.UserRepository
import data.db.table.UsersTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {
    override suspend fun register(email: String, hashedPassword: String): User {
        val id = transaction {
            UsersTable.insert {
                it[UsersTable.email] = email
                it[UsersTable.hashedPassword] = hashedPassword
            } get UsersTable.id
        }
        return User(
            id = id,
            email = email,
            hashedPassword = hashedPassword
        )
    }

    override suspend fun findByEmail(email: String): User? = transaction {
        UsersTable
            .select { UsersTable.email eq email }
            .map {
                User(
                    id = it[UsersTable.id],
                    email = it[UsersTable.email],
                    hashedPassword = it[UsersTable.hashedPassword]
                )
            }
            .firstOrNull()
    }

    override suspend fun existsByEmail(email: String): Boolean = transaction {
        UsersTable
            .select { UsersTable.email eq email }
            .any()
    }

    override suspend fun getById(id: Long): User? = transaction {
        UsersTable
            .select { UsersTable.id eq id }
            .map {
                User(
                    id = it[UsersTable.id],
                    email = it[UsersTable.email],
                    hashedPassword = it[UsersTable.hashedPassword]
                )
            }
            .firstOrNull()
    }
}
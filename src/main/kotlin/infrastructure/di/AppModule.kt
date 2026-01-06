package infrastructure.di

import org.koin.dsl.module
import infrastructure.security.implementation.BCryptPasswordHasher
import infrastructure.security.implementation.JwtTokenService
import danis.galimullin.domain.repository.UserRepository
import infrastructure.security.api.PasswordHasher
import infrastructure.security.api.TokenService
import danis.galimullin.domain.usecase.auth.LoginUserUseCase
import danis.galimullin.domain.usecase.auth.RegisterUserUseCase
import danis.galimullin.domain.usecase.task.CreateTaskUseCase
import domain.usecase.task.DeleteUserTaskUseCase
import domain.usecase.task.ToggleTaskUseCase
import domain.usecase.task.UpdateUserTaskUseCase
import danis.galimullin.domain.usecase.user.GetUserByIdUseCase
import data.repository.TaskRepositoryImpl
import data.repository.UserRepositoryImpl
import domain.repository.TaskRepository
import domain.usecase.task.GetUserTasksUseCase


val appModule = module {
    // Repositories
    single<UserRepository> { UserRepositoryImpl() }
    single<TaskRepository> { TaskRepositoryImpl() }

    // Security
    single<PasswordHasher> { BCryptPasswordHasher() }
    single<TokenService> { JwtTokenService() }

    // Auth useCases
    single { RegisterUserUseCase(get(), get(), get()) } // get() берёт UserRepository и PasswordHasher
    single { LoginUserUseCase(get(), get(), get()) } // UserRepository, PasswordHasher, JwtTokenService

    // Task useCases
    single { CreateTaskUseCase(get()) }
    single { GetUserTasksUseCase(get()) }
    single{ GetUserByIdUseCase(get()) }
    single { UpdateUserTaskUseCase(get()) }
    single { ToggleTaskUseCase(get()) }
    single { DeleteUserTaskUseCase(get()) }

    // User useCases
    single { GetUserByIdUseCase(get()) }
}

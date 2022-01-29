package com.pdm.firebasestoragedatabase.di

import com.pdm.firebasestoragedatabase.feature.data.data_source.AuthDataSourceImpl
import com.pdm.firebasestoragedatabase.feature.data.repository.AuthRepositoryImpl
import com.pdm.firebasestoragedatabase.feature.domain.data_source.AuthDataSource
import com.pdm.firebasestoragedatabase.feature.domain.repository.AuthRepository
import com.pdm.firebasestoragedatabase.feature.domain.use_case.*
import com.pdm.firebasestoragedatabase.feature.domain.use_case.auth.*
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.auth.viewModel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module(override = true) {
    viewModel { AuthViewModel(useCase = get()) }
}

val userCasesModule = module(override = true) {
    single {
        AuthUseCase(
            loginUser = get(),
            registerUser = get(),
            editUser = get(),
            deleteUser = get(),
            recoveryUser = get(),
            submitEmailUserAuth = get(),
            logOut = get()
        )
    }
    single { LoginUserAuth(repository = get()) }
    single { RegisterUserAuth(repository = get()) }
    single { EditUserAuth(repository = get()) }
    single { DeleteUserAuth(repository = get()) }
    single { RecoveryUserAuth(repository = get()) }
    single { SubmitEmailUserAuth(repository = get()) }
    single { LogOutUserAuth(repository = get()) }
}

val repositoryModule = module(override = true) {
    single<AuthRepository> { AuthRepositoryImpl(dataSource = get()) }
}

val dataSourceModule = module(override = true) {
    single<AuthDataSource> { AuthDataSourceImpl() }
}
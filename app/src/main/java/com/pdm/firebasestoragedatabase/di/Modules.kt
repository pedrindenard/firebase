package com.pdm.firebasestoragedatabase.di

import com.pdm.firebasestoragedatabase.feature.data.datasource.LoginDataSourceImpl
import com.pdm.firebasestoragedatabase.feature.data.datasource.ProfileDataSourceImpl
import com.pdm.firebasestoragedatabase.feature.data.datasource.RegisterDataSourceImpl
import com.pdm.firebasestoragedatabase.feature.data.repository.LoginRepositoryImpl
import com.pdm.firebasestoragedatabase.feature.data.repository.ProfileRepositoryImpl
import com.pdm.firebasestoragedatabase.feature.data.repository.RegisterRepositoryImpl
import com.pdm.firebasestoragedatabase.feature.domain.datasource.LoginDataSource
import com.pdm.firebasestoragedatabase.feature.domain.datasource.ProfileDataSource
import com.pdm.firebasestoragedatabase.feature.domain.datasource.RegisterDataSource
import com.pdm.firebasestoragedatabase.feature.domain.repository.LoginRepository
import com.pdm.firebasestoragedatabase.feature.domain.repository.ProfileRepository
import com.pdm.firebasestoragedatabase.feature.domain.repository.RegisterRepository
import com.pdm.firebasestoragedatabase.feature.domain.usecase.AuthUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.login.*
import com.pdm.firebasestoragedatabase.feature.domain.usecase.profile.DeleteUserUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.profile.EditUserUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.profile.GetUserInfoUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.profile.LogOutUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.register.AddInfoUserUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.register.EmailVerificationUseCase
import com.pdm.firebasestoragedatabase.feature.domain.usecase.register.RegisterUserUseCase
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.login.viewmodel.SignInViewModel
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.profile.viewmodel.ProfileViewModel
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.recovery.viewmodel.RecoveryViewModel
import com.pdm.firebasestoragedatabase.feature.presentation.fragment.register.viewmodel.SignUpViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module(override = true) {
    viewModel { SignInViewModel(useCase = get()) }
    viewModel { SignUpViewModel(useCase = get()) }
    viewModel { RecoveryViewModel(useCase = get()) }
    viewModel { ProfileViewModel(useCase = get()) }
}

val userCasesModule = module(override = true) {
    single {
        AuthUseCase(
            loginWithUserUseCase = get(),
            loginWithGoogleUseCase = get(),
            loginWithFacebookUseCase = get(),
            loginWithGitHubUseCase = get(),
            registerUserUseCase = get(),
            addInfoUserUseCase = get(),
            getUserInfoUseCase = get(),
            editUserUseCase = get(),
            deleteUserUseCase = get(),
            recoveryUserUseCase = get(),
            emailVerificationUseCase = get(),
            logOutUseCase = get()
        )
    }
    single { LoginWithUserUseCase(repository = get()) }
    single { LoginWithGoogleUseCase(repository = get()) }
    single { LoginWithFacebookUseCase(repository = get()) }
    single { LoginWithGitHubUseCase(repository = get()) }
    single { RegisterUserUseCase(repository = get()) }
    single { AddInfoUserUseCase(repository = get()) }
    single { GetUserInfoUseCase(repository = get()) }
    single { EditUserUseCase(repository = get()) }
    single { DeleteUserUseCase(repository = get()) }
    single { RecoveryPasswordUseCase(repository = get()) }
    single { EmailVerificationUseCase(repository = get()) }
    single { LogOutUseCase(repository = get()) }
}

val repositoryModule = module(override = true) {
    single<LoginRepository> { LoginRepositoryImpl(dataSource = get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(dataSource = get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(dataSource = get()) }
}

val dataSourceModule = module(override = true) {
    single<LoginDataSource> { LoginDataSourceImpl() }
    single<RegisterDataSource> { RegisterDataSourceImpl() }
    single<ProfileDataSource> { ProfileDataSourceImpl() }
}
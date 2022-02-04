package com.pdm.firebase.di

import com.pdm.firebase.feature.data.datasource.LoginDataSourceImpl
import com.pdm.firebase.feature.data.datasource.PrivacyDataSourceImpl
import com.pdm.firebase.feature.data.datasource.ProfileDataSourceImpl
import com.pdm.firebase.feature.data.datasource.RegisterDataSourceImpl
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.database.ConnectDb
import com.pdm.firebase.feature.data.repository.LoginRepositoryImpl
import com.pdm.firebase.feature.data.repository.PrivacyRepositoryImpl
import com.pdm.firebase.feature.data.repository.ProfileRepositoryImpl
import com.pdm.firebase.feature.data.repository.RegisterRepositoryImpl
import com.pdm.firebase.feature.domain.datasource.LoginDataSource
import com.pdm.firebase.feature.domain.datasource.PrivacyDataSource
import com.pdm.firebase.feature.domain.datasource.ProfileDataSource
import com.pdm.firebase.feature.domain.datasource.RegisterDataSource
import com.pdm.firebase.feature.domain.repository.LoginRepository
import com.pdm.firebase.feature.domain.repository.PrivacyRepository
import com.pdm.firebase.feature.domain.repository.ProfileRepository
import com.pdm.firebase.feature.domain.repository.RegisterRepository
import com.pdm.firebase.feature.domain.usecase.AuthUseCase
import com.pdm.firebase.feature.domain.usecase.PrivacyUseCase
import com.pdm.firebase.feature.domain.usecase.login.*
import com.pdm.firebase.feature.domain.usecase.profile.DeleteUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.EditUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.GetUserInfoUseCase
import com.pdm.firebase.feature.domain.usecase.profile.LogOutUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.EmailVerificationUseCase
import com.pdm.firebase.feature.domain.usecase.register.RegisterUserUseCase
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignInViewModel
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignUpViewModel
import com.pdm.firebase.feature.presentation.fragment.privacy.viewmodel.PrivacyViewModel
import com.pdm.firebase.feature.presentation.fragment.profile.viewmodel.ProfileViewModel
import com.pdm.firebase.feature.presentation.fragment.recovery.viewmodel.RecoveryViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module(override = true) {
    viewModel { SignInViewModel(useCase = get()) }
    viewModel { SignUpViewModel(useCase = get()) }
    viewModel { RecoveryViewModel(useCase = get()) }
    viewModel { ProfileViewModel(useCase = get()) }
    viewModel { PrivacyViewModel(useCase = get()) }
}

val userCasesModule = module(override = true) {
    single {
        AuthUseCase(
            loginWithUserUseCase = get(),
            loginWithGoogleUseCase = get(),
            loginWithFacebookUseCase = get(),
            loginWithGitHubUseCase = get(),
            loginWithNumberProneUseCase = get(),
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
    single { LoginWithNumberProneUseCase(repository = get()) }
    single { RegisterUserUseCase(repository = get()) }
    single { AddInfoUserUseCase(repository = get()) }
    single { GetUserInfoUseCase(repository = get()) }
    single { EditUserUseCase(repository = get()) }
    single { DeleteUserUseCase(repository = get()) }
    single { RecoveryPasswordUseCase(repository = get()) }
    single { EmailVerificationUseCase(repository = get()) }
    single { LogOutUseCase(repository = get()) }
    single { PrivacyUseCase(repository = get()) }
}

val repositoryModule = module(override = true) {
    single<LoginRepository> { LoginRepositoryImpl(dataSource = get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(dataSource = get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(dataSource = get()) }
    single<PrivacyRepository> { PrivacyRepositoryImpl(dataSource = get()) }
}

val dataSourceModule = module(override = true) {
    single<LoginDataSource> { LoginDataSourceImpl() }
    single<RegisterDataSource> { RegisterDataSourceImpl() }
    single<ProfileDataSource> { ProfileDataSourceImpl() }
    single<PrivacyDataSource> { PrivacyDataSourceImpl() }
}

val dataModule = module {
    single { CacheImpl(context = androidContext()) }
    single { ConnectDb.getInstance(context = androidContext()) }
}
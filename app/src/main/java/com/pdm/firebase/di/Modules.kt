package com.pdm.firebase.di

import com.pdm.firebase.App
import com.pdm.firebase.feature.data.datasource.*
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.database.ConnectDb
import com.pdm.firebase.feature.data.repository.*
import com.pdm.firebase.feature.data.retrofit.ClientRetrofit
import com.pdm.firebase.feature.domain.datasource.*
import com.pdm.firebase.feature.domain.repository.*
import com.pdm.firebase.feature.domain.usecase.AuthUseCase
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.domain.usecase.PrivacyUseCase
import com.pdm.firebase.feature.domain.usecase.login.*
import com.pdm.firebase.feature.domain.usecase.movie.*
import com.pdm.firebase.feature.domain.usecase.profile.DeleteUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.EditUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.GetUserInfoUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoSocialUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.EmailVerificationUseCase
import com.pdm.firebase.feature.domain.usecase.register.RegisterUserUseCase
import com.pdm.firebase.feature.presentation.fragment.gender.viewmodel.GenderViewModel
import com.pdm.firebase.feature.presentation.fragment.home.viewmodel.HomeViewModel
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignInViewModel
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignUpViewModel
import com.pdm.firebase.feature.presentation.fragment.privacy.viewmodel.PrivacyViewModel
import com.pdm.firebase.feature.presentation.fragment.profile.viewmodel.ProfileViewModel
import com.pdm.firebase.feature.presentation.fragment.recovery.viewmodel.RecoveryViewModel
import com.pdm.firebase.feature.presentation.fragment.splash.viewModel.SplashViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module(override = true) {
    viewModel { SplashViewModel(useCase = get(), cache = get()) }
    viewModel { HomeViewModel(useCase = get(), cache = get()) }
    viewModel { SignInViewModel(useCase = get()) }
    viewModel { SignUpViewModel(useCase = get()) }
    viewModel { RecoveryViewModel(useCase = get()) }
    viewModel { ProfileViewModel(useCase = get()) }
    viewModel { PrivacyViewModel(useCase = get()) }
    viewModel { GenderViewModel(useCase = get()) }
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
            addInfoSocialUserUseCase = get(),
            addInfoUserUseCase = get(),
            getUserInfoUseCase = get(),
            editUserUseCase = get(),
            deleteUserUseCase = get(),
            recoveryUserUseCase = get(),
            emailVerificationUseCase = get()
        )
    }
    single {
        MovieUseCase(
            getBestActors = get(),
            getGendersMovie = get(),
            getMovieByGender = get(),
            getGendersTv = get(),
            getPopularMovie = get(),
            getRatedMovie = get(),
            getSuperBanner = get(),
            getUpcomingMovie = get ()
        )
    }
    single { GetBestActorsUseCase(repository = get()) }
    single { GetGendersMovieUseCase(repository = get()) }
    single { GetGendersTvUseCase(repository = get()) }
    single { GetMovieByGenderUseCase(repository = get()) }
    single { GetPopularMovieUseCase(repository = get()) }
    single { GetRatedMovieUseCase(repository = get()) }
    single { GetSuperBannerUseCase(repository = get()) }
    single { GetUpcomingMovieUseCase(repository = get()) }
    single { LoginWithUserUseCase(repository = get()) }
    single { LoginWithGoogleUseCase(repository = get()) }
    single { LoginWithFacebookUseCase(repository = get()) }
    single { LoginWithGitHubUseCase(repository = get()) }
    single { LoginWithNumberProneUseCase(repository = get()) }
    single { RegisterUserUseCase(repository = get()) }
    single { AddInfoSocialUserUseCase(repository = get()) }
    single { AddInfoUserUseCase(repository = get()) }
    single { GetUserInfoUseCase(repository = get()) }
    single { EditUserUseCase(repository = get()) }
    single { DeleteUserUseCase(repository = get()) }
    single { RecoveryPasswordUseCase(repository = get()) }
    single { EmailVerificationUseCase(repository = get()) }
    single { PrivacyUseCase(repository = get()) }
}

val repositoryModule = module(override = true) {
    single<LoginRepository> { LoginRepositoryImpl(dataSource = get()) }
    single<RegisterRepository> { RegisterRepositoryImpl(dataSource = get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(dataSource = get()) }
    single<PrivacyRepository> { PrivacyRepositoryImpl(dataSource = get()) }
    single<MovieRepository> { MovieRepositoryImpl(dataSource = get(), cache = get()) }
}

val dataSourceModule = module(override = true) {
    single<LoginDataSource> { LoginDataSourceImpl() }
    single<RegisterDataSource> { RegisterDataSourceImpl() }
    single<ProfileDataSource> { ProfileDataSourceImpl() }
    single<PrivacyDataSource> { PrivacyDataSourceImpl() }
    single<MovieDataSource> { MovieDataSourceImpl(api = get(), cache = get()) }
}

val dataModule = module {
    single { App() }
    single { CacheImpl(context = androidContext()) }
    single { ConnectDb.getInstance(context = androidContext()) }
    single { ClientRetrofit.create() }
}
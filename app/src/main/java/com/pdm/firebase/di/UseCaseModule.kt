package com.pdm.firebase.di

import com.pdm.firebase.feature.domain.usecase.AuthUseCase
import com.pdm.firebase.feature.domain.usecase.MovieUseCase
import com.pdm.firebase.feature.domain.usecase.PrivacyUseCase
import com.pdm.firebase.feature.domain.usecase.SearchUseCase
import com.pdm.firebase.feature.domain.usecase.login.*
import com.pdm.firebase.feature.domain.usecase.movie.*
import com.pdm.firebase.feature.domain.usecase.profile.DeleteUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.EditUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.GetUserInfoUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoSocialUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.EmailVerificationUseCase
import com.pdm.firebase.feature.domain.usecase.register.RegisterUserUseCase
import com.pdm.firebase.feature.domain.usecase.search.*
import org.koin.dsl.module

object UseCaseModule {
    val get = module(override = true) {
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
                getUpcomingMovie = get(),
                getNowPlayingMovie = get(),
            )
        }
        single {
            SearchUseCase(
                getRegions = get(),
                getSearchCollections = get(),
                getSearchMovies = get(),
                getSearchMulti = get(),
                getSearchPeople = get(),
                getSearchTvShows = get()
            )
        }
        single { GetRegionsUseCase(repository = get()) }
        single { GetSearchCollectionUseCase(repository = get()) }
        single { GetSearchMovieUseCase(repository = get()) }
        single { GetSearchMultiUseCase(repository = get()) }
        single { GetSearchPeopleUseCase(repository = get()) }
        single { GetSearchTvShowsUseCase(repository = get()) }
        single { GetNowPlayingMovieUseCase(repository = get()) }
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
}
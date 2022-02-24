package com.pdm.firebase.di

import com.pdm.firebase.feature.domain.usecase.*
import com.pdm.firebase.feature.domain.usecase.discover.GetMovieByQueryUseCase
import com.pdm.firebase.feature.domain.usecase.discover.GetTvShowByQueryUseCase
import com.pdm.firebase.feature.domain.usecase.login.*
import com.pdm.firebase.feature.domain.usecase.movie.*
import com.pdm.firebase.feature.domain.usecase.movie.details.*
import com.pdm.firebase.feature.domain.usecase.people.GetBestActorsUseCase
import com.pdm.firebase.feature.domain.usecase.people.details.GetPeopleCreditsUseCase
import com.pdm.firebase.feature.domain.usecase.people.details.GetPeopleDetailsUseCase
import com.pdm.firebase.feature.domain.usecase.people.details.GetPeopleImagesUseCase
import com.pdm.firebase.feature.domain.usecase.people.details.GetPeopleTaggedUseCase
import com.pdm.firebase.feature.domain.usecase.profile.DeleteUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.EditUserUseCase
import com.pdm.firebase.feature.domain.usecase.profile.GetUserInfoUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoSocialUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.AddInfoUserUseCase
import com.pdm.firebase.feature.domain.usecase.register.EmailVerificationUseCase
import com.pdm.firebase.feature.domain.usecase.register.RegisterUserUseCase
import com.pdm.firebase.feature.domain.usecase.search.*
import com.pdm.firebase.feature.domain.usecase.tv.*
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
                getGendersMovie = get(),
                getMovieByGender = get(),
                getPopularMovie = get(),
                getRatedMovie = get(),
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
        single {
            DiscoverUseCase(
                getMovieByQuery = get(),
                getTvShowByQuery = get()
            )
        }
        single {
            TvShowUseCase(
                getTvShowPopular = get(),
                getTvShowTopRated = get(),
                getGendersTvShow = get(),
                getTvShowByGender = get(),
                getTvShowOnAir = get()
            )
        }
        single {
            MovieDetailsUseCase(
                getMovieCredits = get(),
                getMovieDetails = get(),
                getMovieImages = get(),
                getMovieProviders = get(),
                getMovieRecommendations = get(),
                getMovieReviews = get(),
                getMovieSimilar = get(),
                getMovieVideos = get(),
            )
        }
        single {
            PeopleUseCase(
                getPeopleCredits = get(),
                getPeopleDetails = get(),
                getPeopleImages = get(),
                getPeopleTagged = get(),
                getBestActors = get()
            )
        }
        single { GetPeopleCreditsUseCase(repository = get()) }
        single { GetPeopleDetailsUseCase(repository = get()) }
        single { GetPeopleImagesUseCase(repository = get()) }
        single { GetPeopleTaggedUseCase(repository = get()) }
        single { GetMovieCreditsUseCase(repository = get()) }
        single { GetMovieDetailsUseCase(repository = get()) }
        single { GetMovieImagesUseCase(repository = get()) }
        single { GetMovieProvidersUseCase(repository = get()) }
        single { GetMovieRecommendationsUseCase(repository = get()) }
        single { GetMovieReviewsUseCase(repository = get()) }
        single { GetMovieSimilarUseCase(repository = get()) }
        single { GetMovieVideosUseCase(repository = get()) }
        single { GetTvShowOnAirUseCase(repository = get()) }
        single { GetTvShowByGenderUseCase(repository = get()) }
        single { GetTvShowTopRatedUseCase(repository = get()) }
        single { GetTvShowPopularUseCase(repository = get()) }
        single { GetMovieByQueryUseCase(repository = get()) }
        single { GetTvShowByQueryUseCase(repository = get()) }
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
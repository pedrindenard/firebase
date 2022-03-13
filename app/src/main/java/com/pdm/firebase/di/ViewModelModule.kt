package com.pdm.firebase.di

import com.pdm.firebase.feature.presentation.fragment.details.movie.viewmodel.MovieViewModel
import com.pdm.firebase.feature.presentation.fragment.details.people.viewmodel.PeopleViewModel
import com.pdm.firebase.feature.presentation.fragment.details.tv.viewmodel.TvShowViewModel
import com.pdm.firebase.feature.presentation.fragment.discover.viewmodel.DiscoveryViewModel
import com.pdm.firebase.feature.presentation.fragment.gender.viewmodel.GenderViewModel
import com.pdm.firebase.feature.presentation.fragment.home.viewmodel.HomeViewModel
import com.pdm.firebase.feature.presentation.fragment.init.viewModel.SplashViewModel
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignInViewModel
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignUpViewModel
import com.pdm.firebase.feature.presentation.fragment.privacy.viewmodel.PrivacyViewModel
import com.pdm.firebase.feature.presentation.fragment.profile.viewmodel.ProfileViewModel
import com.pdm.firebase.feature.presentation.fragment.recovery.viewmodel.RecoveryViewModel
import com.pdm.firebase.feature.presentation.fragment.search.viewmodel.SearchViewModel
import com.pdm.firebase.feature.presentation.fragment.upcoming.viewmodel.UpComingViewModel
import com.pdm.firebase.feature.presentation.fragment.video.viewmodel.VideoViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val get = module(override = true) {
        viewModel {
            HomeViewModel(
                movieDetailsUseCase = get(),
                tvDetailsUseCase = get(),
                peopleUseCase = get(),
                tvShowUseCase = get(),
                movieUseCase = get(),
                cache = get()
            )
        }
        viewModel {
            DiscoveryViewModel(
                movieUseCase = get(),
                tvShowUseCase = get(),
                discoverUseCase = get()
            )
        }
        viewModel {
            SplashViewModel(
                tvShowUseCase = get(),
                movieUseCase = get(),
                cache = get()
            )
        }
        viewModel {
            UpComingViewModel(
                detailsUseCase = get(),
                movieUseCase = get()
            )
        }
        viewModel {
            GenderViewModel(
                tvShowUseCase = get(),
                movieUseCase = get()
            )
        }
        viewModel {
            VideoViewModel(
                movieDetailsUseCase = get(),
                tvDetailsUserCase = get(),
                movieUseCase = get(),
                tvUserCase = get()
            )
        }
        viewModel { TvShowViewModel(useCase = get()) }
        viewModel { SearchViewModel(useCase = get()) }
        viewModel { SignInViewModel(useCase = get()) }
        viewModel { SignUpViewModel(useCase = get()) }
        viewModel { RecoveryViewModel(useCase = get()) }
        viewModel { ProfileViewModel(useCase = get()) }
        viewModel { PrivacyViewModel(useCase = get()) }
        viewModel { MovieViewModel(useCase = get()) }
        viewModel { PeopleViewModel(useCase = get()) }
    }
}
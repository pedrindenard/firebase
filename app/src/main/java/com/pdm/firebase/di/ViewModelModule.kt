package com.pdm.firebase.di

import com.pdm.firebase.feature.presentation.fragment.details.movie.viewmodel.MovieViewModel
import com.pdm.firebase.feature.presentation.fragment.details.people.viewmodel.PeopleViewModel
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
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val get = module(override = true) {
        viewModel { HomeViewModel(movieUseCase = get(), tvShowUseCase = get(), cache = get(), peopleUseCase = get()) }
        viewModel { DiscoveryViewModel(movieUseCase = get(), tvShowUseCase = get(), discoverUseCase = get()) }
        viewModel { SplashViewModel(movieUseCase = get(), tvShowUseCase = get(), cache = get()) }
        viewModel { GenderViewModel(movieUseCase = get(), tvShowUseCase = get()) }
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
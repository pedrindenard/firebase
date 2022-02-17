package com.pdm.firebase.di

import com.pdm.firebase.feature.presentation.fragment.gender.viewmodel.GenderViewModel
import com.pdm.firebase.feature.presentation.fragment.home.viewmodel.HomeViewModel
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignInViewModel
import com.pdm.firebase.feature.presentation.fragment.login.viewmodel.SignUpViewModel
import com.pdm.firebase.feature.presentation.fragment.privacy.viewmodel.PrivacyViewModel
import com.pdm.firebase.feature.presentation.fragment.profile.viewmodel.ProfileViewModel
import com.pdm.firebase.feature.presentation.fragment.recovery.viewmodel.RecoveryViewModel
import com.pdm.firebase.feature.presentation.fragment.search.viewmodel.SearchViewModel
import com.pdm.firebase.feature.presentation.fragment.splash.viewModel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val get = module(override = true) {
        viewModel { SplashViewModel(useCase = get(), cache = get()) }
        viewModel { HomeViewModel(useCase = get(), cache = get()) }
        viewModel { SearchViewModel(useCase = get()) }
        viewModel { SignInViewModel(useCase = get()) }
        viewModel { SignUpViewModel(useCase = get()) }
        viewModel { RecoveryViewModel(useCase = get()) }
        viewModel { ProfileViewModel(useCase = get()) }
        viewModel { PrivacyViewModel(useCase = get()) }
        viewModel { GenderViewModel(useCase = get()) }
    }
}
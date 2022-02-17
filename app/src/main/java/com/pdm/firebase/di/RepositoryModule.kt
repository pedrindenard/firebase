package com.pdm.firebase.di

import com.pdm.firebase.feature.data.repository.*
import com.pdm.firebase.feature.domain.repository.*
import org.koin.dsl.module

object RepositoryModule {
    val get = module(override = true) {
        single<LoginRepository> { LoginRepositoryImpl(dataSource = get()) }
        single<RegisterRepository> { RegisterRepositoryImpl(dataSource = get()) }
        single<ProfileRepository> { ProfileRepositoryImpl(dataSource = get()) }
        single<PrivacyRepository> { PrivacyRepositoryImpl(dataSource = get()) }
        single<MovieRepository> { MovieRepositoryImpl(dataSource = get(), cache = get()) }
        single<SearchRepository> { SearchRepositoryImpl(dataSource = get(), cache = get()) }
    }
}
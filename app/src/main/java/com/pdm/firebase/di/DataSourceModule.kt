package com.pdm.firebase.di

import com.pdm.firebase.feature.data.datasource.*
import com.pdm.firebase.feature.domain.datasource.*
import org.koin.dsl.module

object DataSourceModule {
    val get = module(override = true) {
        single<LoginDataSource> { LoginDataSourceImpl() }
        single<RegisterDataSource> { RegisterDataSourceImpl() }
        single<ProfileDataSource> { ProfileDataSourceImpl() }
        single<PrivacyDataSource> { PrivacyDataSourceImpl() }
        single<MovieDataSource> { MovieDataSourceImpl(api = get(), cache = get()) }
        single<SearchDataSource> { SearchDataSourceImpl(api = get(), cache = get()) }
    }
}
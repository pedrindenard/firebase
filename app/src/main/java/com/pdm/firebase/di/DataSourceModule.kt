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
        single<DiscoveryDataSource> { DiscoveryDataSourceImpl(api = get()) }
        single<MovieDataSource> { MovieDataSourceImpl(api = get(), cache = get()) }
        single<TvShowDataSource> { TvShowDataSourceImpl(api = get(), cache = get()) }
        single<SearchDataSource> { SearchDataSourceImpl(api = get(), cache = get()) }
        single<PeopleDataSource> { PeopleDataSourceImpl(api = get(), cache = get()) }
        single<MovieDetailsDataSource> { MovieDetailsDataSourceImpl(api = get()) }
        single<PeopleDetailsDataSource> { PeopleDetailsDataSourceImpl(api = get()) }
        single<TvDetailsDataSource> { TvDetailsDataSourceImpl(api = get()) }
    }
}
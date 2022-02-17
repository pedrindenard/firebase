package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.search.*

data class SearchUseCase(
    val getSearchCollections: GetSearchCollectionUseCase,
    val getSearchMovies: GetSearchMovieUseCase,
    val getSearchTvShows: GetSearchTvShowsUseCase,
    val getSearchMulti: GetSearchMultiUseCase,
    val getSearchPeople: GetSearchPeopleUseCase,
    val getRegions: GetRegionsUseCase
)

package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.discover.GetMovieByQueryUseCase
import com.pdm.firebase.feature.domain.usecase.discover.GetTvShowByQueryUseCase

data class DiscoverUseCase (
    val getMovieByQuery: GetMovieByQueryUseCase,
    val getTvShowByQuery: GetTvShowByQueryUseCase
)
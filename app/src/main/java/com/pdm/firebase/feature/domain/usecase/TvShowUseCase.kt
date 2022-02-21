package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.tv.*

data class TvShowUseCase(
    val getTvShowPopular: GetTvShowPopularUseCase,
    val getGendersTvShow: GetGendersTvUseCase,
    val getTvShowByGender: GetTvShowByGenderUseCase,
    val getTvShowTopRated: GetTvShowTopRatedUseCase,
    val getTvShowOnAir: GetTvShowOnAirUseCase
)

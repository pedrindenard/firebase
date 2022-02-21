package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.tv.GetGendersTvUseCase
import com.pdm.firebase.feature.domain.usecase.tv.GetTvShowByGenderUseCase
import com.pdm.firebase.feature.domain.usecase.tv.GetTvShowPopularUseCase
import com.pdm.firebase.feature.domain.usecase.tv.GetTvShowTopRatedUseCase

data class TvShowUseCase(
    val getTvShowPopular: GetTvShowPopularUseCase,
    val getGendersTvShow: GetGendersTvUseCase,
    val getTvShowByGender: GetTvShowByGenderUseCase,
    val getTvShowTopRated: GetTvShowTopRatedUseCase
)

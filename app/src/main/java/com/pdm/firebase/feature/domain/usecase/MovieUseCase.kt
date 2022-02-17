package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.movie.*

data class MovieUseCase(
    val getSuperBanner: GetSuperBannerUseCase,
    val getPopularMovie: GetPopularMovieUseCase,
    val getRatedMovie: GetRatedMovieUseCase,
    val getGendersMovie: GetGendersMovieUseCase,
    val getGendersTv: GetGendersTvUseCase,
    val getMovieByGender: GetMovieByGenderUseCase,
    val getUpcomingMovie: GetUpcomingMovieUseCase,
    val getNowPlayingMovie: GetNowPlayingMovieUseCase,
    val getBestActors: GetBestActorsUseCase
)
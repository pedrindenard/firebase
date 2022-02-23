package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.movie.details.*

data class MovieDetailsUseCase(
    val getMovieDetails: GetMovieDetailsUseCase,
    val getMovieCredits: GetMovieCreditsUseCase,
    val getMovieRecommendations: GetMovieRecommendationsUseCase,
    val getMovieSimilar: GetMovieSimilarUseCase,
    val getMovieProviders: GetMovieProvidersUseCase,
    val getMovieReviews:GetMovieReviewsUseCase,
    val getMovieVideos: GetMovieVideosUseCase,
    val getMovieImages: GetMovieImagesUseCase
)
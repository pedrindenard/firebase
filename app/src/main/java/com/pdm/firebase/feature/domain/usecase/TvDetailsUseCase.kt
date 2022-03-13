package com.pdm.firebase.feature.domain.usecase

import com.pdm.firebase.feature.domain.usecase.tv.details.*

data class TvDetailsUseCase(
    val getTvDetails: GetTvDetailsUseCase,
    val getTvCredits: GetTvCreditsUseCase,
    val getTvRecommendations: GetTvRecommendationsUseCase,
    val getTvSimilar: GetTvSimilarUseCase,
    val getTvProviders: GetTvProvidersUseCase,
    val getTvReviews: GetTvReviewsUseCase,
    val getTvVideos: GetTvVideosUseCase,
    val getTvImages: GetTvImagesUseCase
)
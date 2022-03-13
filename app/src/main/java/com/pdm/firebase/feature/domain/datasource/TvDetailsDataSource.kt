package com.pdm.firebase.feature.domain.datasource

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.movie.CreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.model.tv.details.TvDetailsResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse

interface TvDetailsDataSource {

    suspend fun getTvDetails(id: Int): Resource<TvDetailsResponse?>

    suspend fun getTvCredits(id: Int): Resource<CreditsResponse?>

    suspend fun getTvSimilar(id: Int, page: Int): Resource<TvShowResponse?>

    suspend fun getTvImages(id: Int): Resource<ImageResponse?>

    suspend fun getTvReviews(id: Int, page: Int): Resource<ReviewResponse?>

    suspend fun getTvProviders(id: Int): Resource<ProviderResponse?>

    suspend fun getTvVideos(id: Int): Resource<VideoResponse?>

    suspend fun getTvRecommendations(id: Int, page: Int): Resource<TvShowResponse?>

}
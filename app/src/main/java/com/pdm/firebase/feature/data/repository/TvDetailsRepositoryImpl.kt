package com.pdm.firebase.feature.data.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.datasource.TvDetailsDataSource
import com.pdm.firebase.feature.domain.model.credit.movie.CreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.model.tv.details.TvDetailsResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import com.pdm.firebase.feature.domain.repository.TvDetailsRepository

class TvDetailsRepositoryImpl(private val dataSource: TvDetailsDataSource) : TvDetailsRepository {

    override suspend fun getTvDetails(id: Int): Resource<TvDetailsResponse?> {
        return dataSource.getTvDetails(id)
    }

    override suspend fun getTvCredits(id: Int): Resource<CreditsResponse?> {
        return dataSource.getTvCredits(id)
    }

    override suspend fun getTvSimilar(id: Int, page: Int): Resource<TvShowResponse?> {
        return dataSource.getTvSimilar(id, page)
    }

    override suspend fun getTvImages(id: Int): Resource<ImageResponse?> {
        return dataSource.getTvImages(id)
    }

    override suspend fun getTvReviews(id: Int, page: Int): Resource<ReviewResponse?> {
        return dataSource.getTvReviews(id, page)
    }

    override suspend fun getTvProviders(id: Int): Resource<ProviderResponse?> {
        return dataSource.getTvProviders(id)
    }

    override suspend fun getTvVideos(id: Int): Resource<VideoResponse?> {
        return dataSource.getTvVideos(id)
    }

    override suspend fun getTvRecommendations(id: Int, page: Int): Resource<TvShowResponse?> {
        return dataSource.getTvRecommendations(id, page)
    }
}
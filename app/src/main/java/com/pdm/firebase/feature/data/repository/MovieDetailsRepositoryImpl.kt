package com.pdm.firebase.feature.data.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.datasource.MovieDetailsDataSource
import com.pdm.firebase.feature.domain.model.credit.movie.CreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.movie.details.MovieDetailsResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse
import com.pdm.firebase.feature.domain.repository.MovieDetailsRepository

class MovieDetailsRepositoryImpl(private val dataSource: MovieDetailsDataSource) : MovieDetailsRepository {

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailsResponse?> {
        return dataSource.getMovieDetails(id)
    }

    override suspend fun getMovieCredits(id: Int): Resource<CreditsResponse?> {
        return dataSource.getMovieCredits(id)
    }

    override suspend fun getMovieRecommendations(id: Int, page: Int): Resource<MovieResponse?> {
        return dataSource.getMovieRecommendations(id, page)
    }

    override suspend fun getMovieSimilar(id: Int, page: Int): Resource<MovieResponse?> {
        return dataSource.getMovieSimilar(id, page)
    }

    override suspend fun getMovieProviders(id: Int): Resource<ProviderResponse?> {
        return dataSource.getMovieProviders(id)
    }

    override suspend fun getMovieReviews(id: Int, page: Int): Resource<ReviewResponse?> {
        return dataSource.getMovieReviews(id, page)
    }

    override suspend fun getMovieVideos(id: Int): Resource<VideoResponse?> {
        return dataSource.getMovieVideos(id)
    }

    override suspend fun getMovieImages(id: Int): Resource<ImageResponse?> {
        return dataSource.getMovieImages(id)
    }
}
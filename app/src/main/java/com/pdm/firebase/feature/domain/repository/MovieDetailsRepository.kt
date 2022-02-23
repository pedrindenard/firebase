package com.pdm.firebase.feature.domain.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.credit.CreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.movie.details.MovieDetailsResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse

interface MovieDetailsRepository {

    suspend fun getMovieDetails(id: Int) : Resource<MovieDetailsResponse?>

    suspend fun getMovieCredits(id: Int) : Resource<CreditsResponse?>

    suspend fun getMovieRecommendations(id: Int, page: Int) : Resource<MovieResponse?>

    suspend fun getMovieSimilar(id: Int, page: Int) : Resource<MovieResponse?>

    suspend fun getMovieProviders(id: Int) : Resource<ProviderResponse?>

    suspend fun getMovieReviews(id: Int, page: Int) : Resource<ReviewResponse?>

    suspend fun getMovieVideos(id: Int) : Resource<VideoResponse?>

    suspend fun getMovieImages(id: Int): Resource<ImageResponse?>
}
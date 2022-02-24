package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event
import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeReturn
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.MovieDetailsDataSource
import com.pdm.firebase.feature.domain.model.credit.movie.MovieCreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.movie.details.MovieDetailsResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse

class MovieDetailsDataSourceImpl(private val api: Api) : MovieDetailsDataSource {

    override suspend fun getMovieDetails(id: Int): Resource<MovieDetailsResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieDetails(id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }

    override suspend fun getMovieCredits(id: Int): Resource<MovieCreditsResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieCredits(id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }

    override suspend fun getMovieRecommendations(id: Int, page: Int): Resource<MovieResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieRecommendations(id = id, page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }

    override suspend fun getMovieSimilar(id: Int, page: Int): Resource<MovieResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieSimilar(id = id, page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }

    override suspend fun getMovieProviders(id: Int): Resource<ProviderResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieProviders(id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }

    override suspend fun getMovieReviews(id: Int, page: Int): Resource<ReviewResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieReviews(id = id, page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }

    override suspend fun getMovieVideos(id: Int): Resource<VideoResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieVideos(id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }

    override suspend fun getMovieImages(id: Int): Resource<ImageResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieImages(id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!)
                    }
                }
                else -> {
                    response.errorCallApi {
                        Resource.InvalidAuth(
                            message = response.message()
                        )
                    }
                }
            }
        }
    }
}
package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event
import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeReturn
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.TvDetailsDataSource
import com.pdm.firebase.feature.domain.model.credit.movie.CreditsResponse
import com.pdm.firebase.feature.domain.model.image.ImageResponse
import com.pdm.firebase.feature.domain.model.movie.provider.ProviderResponse
import com.pdm.firebase.feature.domain.model.review.ReviewResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.model.tv.details.TvDetailsResponse
import com.pdm.firebase.feature.domain.model.video.VideoResponse

class TvDetailsDataSourceImpl(private val api: Api) : TvDetailsDataSource {

    override suspend fun getTvDetails(id: Int): Resource<TvDetailsResponse?> {
        return Event.safeCallApi {
            val response = api.getTvDetails(id = id)

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

    override suspend fun getTvCredits(id: Int): Resource<CreditsResponse?> {
        return Event.safeCallApi {
            val response = api.getTvCredits(id = id)

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

    override suspend fun getTvSimilar(id: Int, page: Int): Resource<TvShowResponse?> {
        return Event.safeCallApi {
            val response = api.getTvSimilar(id = id, page = page)

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

    override suspend fun getTvImages(id: Int): Resource<ImageResponse?> {
        return Event.safeCallApi {
            val response = api.getTvImages(id = id)

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

    override suspend fun getTvReviews(id: Int, page: Int): Resource<ReviewResponse?> {
        return Event.safeCallApi {
            val response = api.getTvReviews(id = id, page = page)

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

    override suspend fun getTvProviders(id: Int): Resource<ProviderResponse?> {
        return Event.safeCallApi {
            val response = api.getTvProviders(id = id)

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

    override suspend fun getTvVideos(id: Int): Resource<VideoResponse?> {
        return Event.safeCallApi {
            val response = api.getTvVideos(id = id)

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

    override suspend fun getTvRecommendations(id: Int, page: Int): Resource<TvShowResponse?> {
        return Event.safeCallApi {
            val response = api.getTvRecommendations(id = id, page = page)

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
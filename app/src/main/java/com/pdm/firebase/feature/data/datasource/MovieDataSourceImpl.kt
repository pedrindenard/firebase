package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeCallApi
import com.pdm.firebase.arquitecture.Event.Companion.toJson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.BEST_ACTORS
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.GENDERS_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.GENDERS_TV
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.HOME_BANNER
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.MOVIE_BY_GENDER
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.POPULAR_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.RATED_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.UPCOMING_MOVIE
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.MovieDataSource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse

class MovieDataSourceImpl(private val api: Api, private val cache: CacheImpl) : MovieDataSource {

    override suspend fun getSuperBanner(): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getSuperBanner()

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(HOME_BANNER, it.toJson())
                    })
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

    override suspend fun getPopularMovie(): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getPopularMovie()

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(POPULAR_MOVIE, it.toJson())
                    })
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

    override suspend fun getRatedMovie(): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getRatedMovie()

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(RATED_MOVIE, it.toJson())
                    })
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

    override suspend fun getGendersMovie(): Resource<GenderResponse?> {
        return safeCallApi {
            val response = api.getGendersMovie()

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(GENDERS_MOVIE, it.toJson())
                    })
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

    override suspend fun getGendersTv(): Resource<GenderResponse?> {
        return safeCallApi {
            val response = api.getGendersTv()

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(GENDERS_TV, it.toJson())
                    })
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

    override suspend fun getMovieByGender(id: Int): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getMovieByGender(id = id)

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(MOVIE_BY_GENDER, it.toJson())
                    })
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

    override suspend fun getUpcomingMovie(): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getUpcomingMovie()

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(UPCOMING_MOVIE, it.toJson())
                    })
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

    override suspend fun getBestActors(): Resource<ActorsResponse?> {
        return safeCallApi {
            val response = api.getBestActors()

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(BEST_ACTORS, it.toJson())
                    })
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
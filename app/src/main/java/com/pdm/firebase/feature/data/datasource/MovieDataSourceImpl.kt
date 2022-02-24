package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeReturn
import com.pdm.firebase.arquitecture.Event.Companion.toJson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.GENDERS_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.MOVIE_BY_GENDER
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.POPULAR_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.RATED_MOVIE
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.UPCOMING_MOVIE
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.MovieDataSource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse

class MovieDataSourceImpl(private val api: Api, private val cache: CacheImpl) : MovieDataSource {

    override suspend fun getPopularMovie(page: Int): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getPopularMovie(page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(POPULAR_MOVIE, it.toJson())
                        })
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

    override suspend fun getRatedMovie(page: Int): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getRatedMovie(page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(RATED_MOVIE, it.toJson())
                        })
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

    override suspend fun getGendersMovie(): Resource<GenderResponse?> {
        return safeCallApi {
            val response = api.getGendersMovie()

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(GENDERS_MOVIE, it.toJson())
                        })
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

    override suspend fun getMovieByGender(page: Int, id: Int): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getMovieByGender(page = page, id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(MOVIE_BY_GENDER, it.toJson())
                        })
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

    override suspend fun getUpcomingMovie(page: Int): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getUpcomingMovie(page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(UPCOMING_MOVIE, it.toJson())
                        })
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

    override suspend fun getMovieNowPlaying(page: Int): Resource<MovieResponse?> {
        return safeCallApi {
            val response = api.getMovieNowPlaying(page = page)

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
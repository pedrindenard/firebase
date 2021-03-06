package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeReturn
import com.pdm.firebase.arquitecture.Event.Companion.toJson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.ON_AIR_TV
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.POPULAR_TV
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.TOP_RATED_TV
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.TvShowDataSource
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse

class TvShowDataSourceImpl(private val api: Api, private val cache: CacheImpl) : TvShowDataSource {

    override suspend fun getTvShowPopular(page: Int): Resource<TvShowResponse?> {
        return safeCallApi {
            val response = api.getTvShowPopular(page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(POPULAR_TV, it.toJson())
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

    override suspend fun getTvShowGenders(): Resource<GenderResponse?> {
        return safeCallApi {
            val response = api.getGendersTv()

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(CacheImpl.GENDERS_TV, it.toJson())
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

    override suspend fun getTvShowByGender(page: Int, id: Int): Resource<TvShowResponse?> {
        return safeCallApi {
            val response = api.getTvShowByGender(page = page, id = id)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(CacheImpl.TV_BY_GENDER, it.toJson())
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

    override suspend fun getTvShowTopRated(page: Int): Resource<TvShowResponse?> {
        return safeCallApi {
            val response = api.getTvShowTopRated(page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(TOP_RATED_TV, it.toJson())
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

    override suspend fun getTvShowOnAir(page: Int): Resource<TvShowResponse?> {
        return safeCallApi {
            val response = api.getTvShowOnAir(page = page)

            when {
                response.isSuccessful -> {
                    response.safeReturn {
                        Resource.Success(data = response.body()!!.also {
                            cache.insert(ON_AIR_TV, it.toJson())
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
}
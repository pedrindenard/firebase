package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event
import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.safeReturn
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.DiscoveryDataSource
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse

class DiscoveryDataSourceImpl(private val api: Api) : DiscoveryDataSource {

    override suspend fun getMovieByQuery(id: Int, sort: String, page: Int): Resource<MovieResponse?> {
        return Event.safeCallApi {
            val response = api.getMovieByQuery(id = id, sort = sort, page = page)

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

    override suspend fun getTvShowByQuery(id: Int, sort: String, page: Int): Resource<TvShowResponse?> {
        return Event.safeCallApi {
            val response = api.getTvShowByQuery(id = id, sort = sort, page = page)

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
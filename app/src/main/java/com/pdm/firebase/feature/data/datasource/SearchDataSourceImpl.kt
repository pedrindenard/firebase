package com.pdm.firebase.feature.data.datasource

import com.pdm.firebase.arquitecture.Event
import com.pdm.firebase.arquitecture.Event.Companion.errorCallApi
import com.pdm.firebase.arquitecture.Event.Companion.toJson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.REGIONS
import com.pdm.firebase.feature.data.retrofit.Api
import com.pdm.firebase.feature.domain.datasource.SearchDataSource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.collection.CollectionResponse
import com.pdm.firebase.feature.domain.model.filter.FilterCreated
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.region.RegionResponse
import com.pdm.firebase.feature.domain.model.search.SearchResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse

class SearchDataSourceImpl(
    private val api: Api,
    private val cache: CacheImpl
) : SearchDataSource {

    override suspend fun getSearchCollections(
        query: String, page: Int
    ): Resource<CollectionResponse?> {
        return Event.safeCallApi {
            val response = api.searchCollection(
                query = query, page = page
            )

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body())
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

    override suspend fun getSearchMovies(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<MovieResponse?> {
        return Event.safeCallApi {
            val response = api.searchMovie(
                query = query,
                page = page,
                adult = filter?.adultContent,
                year = filter?.releaseDate?.toInt(),
                region = filter?.region
            )

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body())
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

    override suspend fun getSearchMulti(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<SearchResponse?> {
        return Event.safeCallApi {
            val response = api.searchMulti(
                query = query, page = page, adult = filter?.adultContent, region = filter?.region
            )

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body())
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

    override suspend fun getSearchTvShows(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<TvShowResponse?> {
        return Event.safeCallApi {
            val response = api.searchTvShow(
                query = query,
                page = page,
                adult = filter?.adultContent,
                year = filter?.releaseDate?.toInt()
            )

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body())
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

    override suspend fun getSearchPeoples(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<ActorsResponse?> {
        return Event.safeCallApi {
            val response = api.searchActor(
                query = query, page = page, adult = filter?.adultContent, region = filter?.region
            )

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body())
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

    override suspend fun getRegions(): Resource<RegionResponse?> {
        return Event.safeCallApi {
            val response = api.getRegions()

            when {
                response.isSuccessful -> {
                    Resource.Success(data = response.body().also {
                        cache.insert(REGIONS, it.toJson())
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
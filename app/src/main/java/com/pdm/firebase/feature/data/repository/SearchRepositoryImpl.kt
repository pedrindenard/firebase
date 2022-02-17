package com.pdm.firebase.feature.data.repository

import com.google.gson.Gson
import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.data.local.CacheImpl
import com.pdm.firebase.feature.data.local.CacheImpl.Companion.REGIONS
import com.pdm.firebase.feature.domain.datasource.SearchDataSource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.collection.CollectionResponse
import com.pdm.firebase.feature.domain.model.filter.FilterCreated
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.region.RegionResponse
import com.pdm.firebase.feature.domain.model.search.SearchResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.repository.SearchRepository

class SearchRepositoryImpl(
    private val dataSource: SearchDataSource,
    private val cache: CacheImpl
) : SearchRepository {

    override suspend fun getSearchCollections(
        query: String, page: Int
    ): Resource<CollectionResponse?> {
        return dataSource.getSearchCollections(query, page)
    }

    override suspend fun getSearchMovies(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<MovieResponse?> {
        return dataSource.getSearchMovies(query, page, filter)
    }

    override suspend fun getSearchMulti(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<SearchResponse?> {
        return dataSource.getSearchMulti(query, page, filter)
    }

    override suspend fun getSearchTvShows(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<TvShowResponse?> {
        return dataSource.getSearchTvShows(query, page, filter)
    }

    override suspend fun getSearchPeoples(
        query: String, page: Int, filter: FilterCreated?
    ): Resource<ActorsResponse?> {
        return dataSource.getSearchPeoples(query, page, filter)
    }

    override suspend fun getRegions(): Resource<RegionResponse?> {
        return when {
            cache.get(REGIONS).isEmpty() -> {
                dataSource.getRegions()
            }
            else -> {
                Resource.Success(
                    Gson().fromJson(
                        cache.get(REGIONS),
                        RegionResponse::class.java
                    )
                )
            }
        }
    }
}
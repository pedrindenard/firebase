package com.pdm.firebase.feature.data.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.datasource.DiscoveryDataSource
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import com.pdm.firebase.feature.domain.repository.DiscoveryRepository

class DiscoveryRepositoryImpl(private val dataSource: DiscoveryDataSource) : DiscoveryRepository {

    override suspend fun getMovieByQuery(
        id: Int, sort: String, page: Int
    ): Resource<MovieResponse?> {
        return dataSource.getMovieByQuery(
            id = id, sort = sort, page = page
        )
    }

    override suspend fun getTvShowByQuery(
        id: Int, sort: String, page: Int
    ): Resource<TvShowResponse?> {
        return dataSource.getTvShowByQuery(
            id = id, sort = sort, page = page
        )
    }
}
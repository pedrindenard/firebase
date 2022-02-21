package com.pdm.firebase.feature.domain.datasource

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse

interface DiscoveryDataSource {

    suspend fun getMovieByQuery(id: Int, sort: String, page: Int): Resource<MovieResponse?>

    suspend fun getTvShowByQuery(id: Int, sort: String, page: Int): Resource<TvShowResponse?>

}
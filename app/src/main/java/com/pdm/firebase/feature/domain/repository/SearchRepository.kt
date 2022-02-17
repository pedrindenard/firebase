package com.pdm.firebase.feature.domain.repository

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.collection.CollectionResponse
import com.pdm.firebase.feature.domain.model.filter.FilterCreated
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.region.RegionResponse
import com.pdm.firebase.feature.domain.model.search.SearchResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse

interface SearchRepository {

    suspend fun getSearchCollections(query: String, page: Int): Resource<CollectionResponse?>

    suspend fun getSearchMovies(query: String, page: Int, filter: FilterCreated?): Resource<MovieResponse?>

    suspend fun getSearchMulti(query: String, page: Int, filter: FilterCreated?): Resource<SearchResponse?>

    suspend fun getSearchTvShows(query: String, page: Int, filter: FilterCreated?): Resource<TvShowResponse?>

    suspend fun getSearchPeoples(query: String, page: Int, filter: FilterCreated?): Resource<ActorsResponse?>

    suspend fun getRegions(): Resource<RegionResponse?>

}
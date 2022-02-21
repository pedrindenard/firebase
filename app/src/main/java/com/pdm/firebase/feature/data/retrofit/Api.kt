package com.pdm.firebase.feature.data.retrofit

import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.collection.CollectionResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
import com.pdm.firebase.feature.domain.model.region.RegionResponse
import com.pdm.firebase.feature.domain.model.search.SearchResponse
import com.pdm.firebase.feature.domain.model.tv.TvShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface Api {

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/now_playing")
    suspend fun getSuperBanner(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/popular")
    suspend fun getPopularMovie(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/top_rated")
    suspend fun getRatedMovie(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "genre/movie/list")
    suspend fun getGendersMovie(): Response<GenderResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "genre/tv/list")
    suspend fun getGendersTv(): Response<GenderResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "discover/movie?sort_by=popularity.desc")
    suspend fun getMovieByGender(
        @Query(value = "with_genres") id: Int,
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "discover/tv?sort_by=popularity.desc")
    suspend fun getTvShowByGender(
        @Query(value = "with_genres") id: Int,
        @Query(value = "page") page: Int? = 1
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/upcoming")
    suspend fun getUpcomingMovie(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/popular")
    suspend fun getTvShowPopular(
        @Query(value = "page") page: Int? = 1
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "tv/top_rated")
    suspend fun getTvShowTopRated(
        @Query(value = "page") page: Int? = 1
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "person/popular")
    suspend fun getBestActors(
        @Query(value = "page") page: Int? = 1
    ): Response<ActorsResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "movie/now_playing")
    suspend fun getMovieNowPlaying(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/collection")
    suspend fun searchCollection(
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<CollectionResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/movie")
    suspend fun searchMovie(
        @Query(value = "include_adult") adult: Boolean? = false,
        @Query(value = "region") region: String? = null,
        @Query(value = "year") year: Int? = null,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/tv")
    suspend fun searchTvShow(
        @Query(value = "first_air_date_year") year: Int? = null,
        @Query(value = "include_adult") adult: Boolean? = false,
        @Query(value = "region") region: String? = null,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<TvShowResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/multi")
    suspend fun searchMulti(
        @Query(value = "include_adult") adult: Boolean? = false,
        @Query(value = "region") region: String? = null,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<SearchResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "search/person")
    suspend fun searchActor(
        @Query(value = "include_adult") adult: Boolean? = false,
        @Query(value = "region") region: String? = null,
        @Query(value = "query") query: String,
        @Query(value = "page") page: Int? = 1
    ): Response<ActorsResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "watch/providers/regions")
    suspend fun getRegions() : Response<RegionResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "discover/movie")
    suspend fun getMovieByQuery(
        @Query(value = "sort_by") sort: String,
        @Query(value = "with_genres") id: Int,
        @Query(value = "page") page: Int
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "discover/tv")
    suspend fun getTvShowByQuery(
        @Query(value = "sort_by") sort: String,
        @Query(value = "with_genres") id: Int,
        @Query(value = "page") page: Int
    ): Response<TvShowResponse>
}
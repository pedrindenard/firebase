package com.pdm.firebase.feature.data.retrofit

import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse
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
    @GET(value = "movie/upcoming")
    suspend fun getUpcomingMovie(
        @Query(value = "page") page: Int? = 1
    ): Response<MovieResponse>

    @Headers(value = ["Content-Type: application/json"])
    @GET(value = "person/popular")
    suspend fun getBestActors(
        @Query(value = "page") page: Int? = 1
    ): Response<ActorsResponse>
}
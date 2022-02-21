package com.pdm.firebase.feature.domain.datasource

import com.pdm.firebase.arquitecture.Resource
import com.pdm.firebase.feature.domain.model.actor.ActorsResponse
import com.pdm.firebase.feature.domain.model.gender.GenderResponse
import com.pdm.firebase.feature.domain.model.movie.MovieResponse

interface MovieDataSource {

    suspend fun getSuperBanner(page: Int): Resource<MovieResponse?>

    suspend fun getPopularMovie(page: Int): Resource<MovieResponse?>

    suspend fun getRatedMovie(page: Int): Resource<MovieResponse?>

    suspend fun getGendersMovie(): Resource<GenderResponse?>

    suspend fun getMovieByGender(page: Int, id: Int): Resource<MovieResponse?>

    suspend fun getUpcomingMovie(page: Int): Resource<MovieResponse?>

    suspend fun getMovieNowPlaying(page: Int): Resource<MovieResponse?>

    suspend fun getBestActors(page: Int): Resource<ActorsResponse?>

}